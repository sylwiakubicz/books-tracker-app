package com.example.books_tracker.service;


import com.example.books_tracker.model.UploadResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class UploadService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "credentialGoogleDrive.json");
        return filePath.toString();
    }

    public UploadResponse uploadImageToDrive(File file, String originalFileName) {
        UploadResponse uploadResponse = new UploadResponse();
        try {
            String folderId = "1lvk3W94W7zXXYgT4Ley2JSp8BjKXgea_";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();

            String newFileName = originalFileName.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_\\-\\.]", "");
            fileMetaData.setName(newFileName);

            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpg", file);
            com.google.api.services.drive.model.File uploadFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = uploadFile.getId();
            file.delete();
            uploadResponse.setStatus(200);
            uploadResponse.setMessage("Image uploaded");
            uploadResponse.setUrl(imageUrl);
        } catch (Exception e) {
            uploadResponse.setStatus(500);
            uploadResponse.setMessage(e.getMessage());
        }
        return uploadResponse;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential).build();
    }
}
