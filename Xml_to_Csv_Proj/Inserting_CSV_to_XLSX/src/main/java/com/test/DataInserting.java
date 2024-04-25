package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

public class DataInserting extends AbstractMediator {

	public boolean mediate(MessageContext message) {
		String filePath = "C://SFTP/New folder/WSO2.csv";
		CharSequence csvData = (CharSequence) message.getProperty("ReadRequest");
		if (csvData == null) {
			message.setProperty("JavaCsvData", "Request CSV Data is empty ....!");
		} else {

			message.setProperty("JavaCsvData", csvData);

			File file = new File(filePath);
			if (file.exists()) {
				message.setProperty("FileExitMessage", "file exist");

				// Split the input data into lines
				String[] lines = csvData.toString().split("\n");

				// Skip the first line (headers)
				CharSequence[] dataWithoutHeaders = new CharSequence[lines.length - 1];
				System.arraycopy(lines, 1, dataWithoutHeaders, 0, lines.length - 1);

				// Join the lines back into a single CharSequence
				CharSequence dataWithoutHeadersAsString = String.join("\n", dataWithoutHeaders);

				message.setProperty("DataWithoutHeader", dataWithoutHeadersAsString);

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
					if (dataWithoutHeaders.length != 0) {

						bw.newLine();
						bw.append(dataWithoutHeadersAsString);
						
						message.setProperty("DataSucess", "CSV data appended successfully");
					}
					bw.close();
				} catch (IOException e) {
					message.setProperty("DataFail", "Error appending CSV data");
				}

			} else {
				message.setProperty("FileNotExistMessage", "file Not exist");

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
				
					bw.append(csvData);
					bw.close();

					message.setProperty("DataSucess", "CSV data appended successfully");
				} catch (IOException e) {
					message.setProperty("DataFail", "Error appending CSV data");
				}
			}

		}

		return true;
	}

}
