package webapp.newbooking.image;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.File;
@Component
public class image extends ResourceHttpRequestHandler {
    public static final String ATTRIBUTE_FILE = "DOWNLOADING_FILE";
    @Override
    protected Resource getResource(HttpServletRequest request) {
        File file = (File) request.getAttribute(ATTRIBUTE_FILE);
        return new FileSystemResource(file);
    }
}
