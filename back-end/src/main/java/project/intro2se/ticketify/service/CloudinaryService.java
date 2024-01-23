package project.intro2se.ticketify.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;
    @Async
    public Future<Map> upload(MultipartFile file)  {
        try{

            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return CompletableFuture.completedFuture(data);
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }
}
