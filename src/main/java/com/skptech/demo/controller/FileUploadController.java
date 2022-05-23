package com.skptech.demo.controller;

import com.skptech.demo.model.UploadedFile;
import com.skptech.demo.service.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(
                        FileUploadController.class,
                        "serveFile",
                        path.getFileName().toString()
                ).build().toUri().toString()
        ).collect(Collectors.toList()));

        AtomicInteger count = new AtomicInteger(1);
        model.addAttribute("uploadedFiles", storageService.loadAll().map(
                path -> new UploadedFile(
                        count.getAndIncrement(),
                        path.getFileName().toString(),
                        MvcUriComponentsBuilder.fromMethodName(
                                FileUploadController.class,
                                "serveFile",
                                path.getFileName().toString()
                        ).build().toUri().toString()
                )
        ).collect(Collectors.toList()));

        return "upload";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        System.out.println("serveFile invoked");
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        storageService.store(multipartFile);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + multipartFile.getOriginalFilename() + "!");
        return "redirect:/";
    }

}
