package com.molveno.restaurantReservation.services;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AzureBlobUploadService {

    // Replace with your connection string and container name
    private static final String CONNECTION_STRING = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
    private static final String CONTAINER_NAME = "images";

    public String uploadImageToAzure(MultipartFile imageFile, String name) throws IOException {
        // Create a BlobServiceClient object to connect to the Blob Storage account
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();

        // Get a reference to the Blob container
        BlobContainerClient blobContainerClient = blobServiceClient
                .getBlobContainerClient(CONTAINER_NAME);

        // Generate a unique file name for the image
        String fileName = "image-" + name + "-" + imageFile.getOriginalFilename();

        // Get a reference to a blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the image file to the Blob Storage
        blobClient.upload(imageFile.getInputStream(), imageFile.getSize(), true);

        // Return the Blob URL
        return blobClient.getBlobUrl();
    }
}
