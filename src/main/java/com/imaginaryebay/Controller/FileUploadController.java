package com.imaginaryebay.Controller;

/**
 * Created by Brian on 6/29/2016.
 */


import com.imaginaryebay.Models.S3FileUploader;
import com.imaginaryebay.Repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController {

    private static final String UPLOAD_LOCATION = "C:/Users/Brian/Desktop/";
    private static final String FAIL_EMPTY_FILES = "Unable to upload. File is empty.";
    private static final String FAIL_UPLOAD_ERR = "You failed to upload ";
    private static final String COLON_SEP = ": ";
    private static final String HTML_BREAK = "</br>";

    private ItemRepository itemRepository;

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String getHomePage(ModelMap model) {
        return "welcome";
    }

    @RequestMapping(value = "/multipleUpload")
    public String multiUpload() {
        return "multipleUpload";
    }


    // TODO: Doesn't really qualify as a DAO. Should we place this in a class or leave here?
    @RequestMapping(value = "/multipleSave", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> multipleSave(@RequestParam("file") MultipartFile[] files) {

        String fileName = null;
        String message = "";

        if (files != null && files.length > 0) {
            for (MultipartFile mpFile : files) {
                try {
                    fileName = mpFile.getOriginalFilename();

                    S3FileUploader s3FileUploader = new S3FileUploader();
                    message = s3FileUploader.fileUploader(mpFile);

                } catch (Exception e) {
                    message = FAIL_UPLOAD_ERR + fileName + COLON_SEP + e.getMessage() + HTML_BREAK;
                    return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<String>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }
}

