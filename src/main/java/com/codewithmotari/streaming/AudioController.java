package com.codewithmotari.streaming;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AudioController {

    @GetMapping("/stream-audio")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> streamAudio() throws IOException {
        File file = new File("/home/motaribrian/Music/AFROBEAT MASTERPIECE (24, 23, 22), AFROBEATS VIDEO MIX, NAIJA AFROBEAT MIX - AYRA STARR, REMA, BURNA.m4a");
        System.out.println("reading from ..." + file.getCanonicalPath());
      StreamingResponseBody body=outputStream -> {
          try(InputStream in=new FileInputStream(file)){
              int n;
              byte [] bytes=new byte[4098];
              while ((n=in.read(bytes))!=1){
                  outputStream.write(bytes,0,n);
              }
          } catch (IOException e) {
              System.out.println(e.getMessage());
              throw new RuntimeException(e);
          }
      };

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mp4"))
                .body(body);
    }

    @GetMapping("/music")
    public String musicPage(Model model) {
        model.addAttribute("streamUrl", "/stream-audio");
        return "audio";
    }
}
