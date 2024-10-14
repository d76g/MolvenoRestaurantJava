package com.molveno.restaurantReservation;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantReservationApplication {

	public static void main(String[] args) {

		SpringApplication.run(RestaurantReservationApplication.class, args);

		// Retrieve the connection string for use with the application.
		String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

		// Create a BlobServiceClient object using a connection string
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.connectionString(connectStr)
				.buildClient();
	}

}
