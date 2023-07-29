package com.assessment;

import com.github.javafaker.Faker;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Locale;

@SpringBootTest
class AssessmentApplicationTests {

	@Ignore
	@Test
	void contextLoads() {
	}

	@Ignore
	@Test
	void dummyDataCsvGenerator(){
		Faker faker = new Faker(new Locale("en")); // Use the English locale for consistent names
		String csvFilePath = "dummy_data.csv";
		int numberOfRecords = 10000000;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));
			 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

			for (int i = 0; i < numberOfRecords; i++) {
				String name = faker.name().fullName();
				String fatherName = faker.name().fullName();
				String motherName = faker.name().fullName();
				String email = faker.internet().emailAddress();
				String phoneNumber = faker.phoneNumber().phoneNumber();
				String passportNumber = faker.idNumber().ssnValid();

				// Write the generated data to the CSV file
				csvPrinter.printRecord(name, fatherName, motherName, email, phoneNumber, passportNumber);
			}

			System.out.println("CSV file generated successfully.");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}



}
