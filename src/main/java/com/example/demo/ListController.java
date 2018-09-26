package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;



@RestController
@CrossOrigin
public class ListController {
	
	public int sema = 0;
	public Vector<String> requestVideoVector = new Vector<String>();
	
	List<ListClass> vlist = new ArrayList<ListClass>();
	TcpServer tcpServer;
	CppServer cppserver;
	File file;
	boolean isFirst = true;
	
	//생성자가 불릴 때 ServerSocket 활성화
	public ListController(){
		if(isFirst == true) {
		tcpServer = new TcpServer();
		System.out.println("new TcpServer!!!");
		cppserver = new CppServer(tcpServer);
		System.out.println("new cppServer!!!");
		
		isFirst = false;
		}
	}
	
	@Autowired
	DAO dao;
	
	
	//WebServer에게 DataBase목록을 주는 Method
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<ListClass>> getList(){
		System.out.println("get react request OK");
		ArrayList<ListClass> a = new ArrayList<ListClass>();
		a = (ArrayList<ListClass>) dao.getList();
		Gson gson = new Gson();
		gson.toJson(a);
		return new ResponseEntity<ArrayList<ListClass>>(a, HttpStatus.OK);
	}
	
	
	//동영상 Download를 위한 Method
	@RequestMapping(value = "/and", method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String> goAndroid(@RequestBody HashMap<String, String> input) {
		System.out.println("react post and ok");
		
		//wait
		System.out.println("before : " + requestVideoVector.size());
		
		requestVideoVector.add(input.get("videoName"));
		
		System.out.println("after : " + requestVideoVector.size());
		
		while(requestVideoVector.size()!=0 && sema == 0)
		{
			sema = 1;
			file = new File("C:\\Users\\hojin\\Desktop\\file\\" + requestVideoVector.get(0).trim());
			tcpServer.file = file;
		
			tcpServer.filelength = ((long)file.length());
			System.out.println(tcpServer.filelength+"");
		
			tcpServer.fileSend();
		
			sema = 0;
			requestVideoVector.remove(0);
		}
		
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
}
