package com.it.sero.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.it.sero.model.Document;
import com.it.sero.service.IDocumentService;

@Controller
@RequestMapping("/doc")
public class DocumentServiceController {
	
	@Autowired
	private  IDocumentService service;
	
	@GetMapping
	@RequestMapping("/all")
	public String uploadDoc(Model model)
	{
		List<Object[]>list=service.getDocumentIdandNames();
		model.addAttribute("list",list);
		return "Documents";
	}

	@PostMapping
	@RequestMapping("/save")
	public String saveDoc(@RequestParam Integer fid,@RequestParam MultipartFile fob) throws IOException
	{
		try {
			//create Model class object
			Document doc = new Document();
			doc.setDocId(fid);
			doc.setDocName(fob.getOriginalFilename());
			doc.setDocDatata(fob.getBytes());
			service.saveDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	@GetMapping("/download")
	public void  downloadDoc(@RequestParam Integer id,HttpServletResponse resp)
	{
		//a. get data based on id
		Optional<Document> opt = service.getOneDocumnet(id);
		
		if(opt.isPresent()) {
			//b. read object
			Document doc = opt.get();
			
			//c. add Header Param
			resp.addHeader("Content-Disposition", "attachment;filename="+doc.getDocName());
			
			//d. copy data //from --> to
			try {
				FileCopyUtils.copy(doc.getDocDatata(), resp.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
