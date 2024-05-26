

# LabelPrinterSDKSR20

The `LabelPrinterSDKSR20` is a Java library designed to simplify interactions with the SR 20 label printer. It abstracts low-level commands into high-level methods for setting up the printer, specifying paper settings, printing graphics, and more.

## Installation

# Gradle

Add the following to your `build.gradle`:

groovy
dependencies {
implementation 'com.microlan:labelprinter:1.0-SNAPSHOT'
}

# Maven

Add the following to your 'pom.xml':

xml
Copy code
<dependency>
<groupId>com.microlan</groupId>
<artifactId>labelprinter</artifactId>
<version>1.0-SNAPSHOT</version>
</dependency>


# Usage
````
Initializing the SDK
To use the SDK, create an instance of the LabelPrinterSDKSR20 class by passing an OutputStream and an InputStream that communicate with your printer:

java
Copy code
OutputStream printerOutput = ...; // Obtain printer output stream
InputStream printerInput = ...;   // Obtain printer input stream
LabelPrinterSDKSR20 printer = new LabelPrinterSDKSR20(printerOutput, printerInput);


Setting Up Paper Type
java
Copy code
try {
    printer.setUpPaperType(2); // Set the paper type
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to set up paper type: " + e.getMessage());
}


Setting Paper Specifications
java
Copy code
try {
    printer.setPaperSpecifications(356, 304, 576, 30); // Set paper specifications
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to set paper specifications: " + e.getMessage());
}


Printing Graphics
java
Copy code
try {
    byte[] graphicData = new byte[] { (byte) 0x01, (byte) 0xFC, (byte) 0x07, (byte) 0xFF };
    printer.graphicPrint(graphicData); // Print graphic
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to print graphic: " + e.getMessage());
}


Moving the Printing Position
java
Copy code
try {
    printer.movePrintingPosition(100, 200); // Move printing position
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to move printing position: " + e.getMessage());
}


Querying the Printer Status
java
Copy code
try {
    byte[] status = printer.queryPrinterStatus(); // Query printer status
    System.out.println(Arrays.toString(status));
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to query printer status: " + e.getMessage());
}


API Reference
LabelPrinterSDKSR20
LabelPrinterSDKSR20(OutputStream printerOutput, InputStream printerInput)
Description: Constructor to initialize the SDK with the printer's output and input streams.
Parameters:
printerOutput (OutputStream): The output stream to send data to the printer.
printerInput (InputStream): The input stream to read data from the printer.
void setUpPaperType(int paperType) throws LabelPrinterSDKException
Description: Sets up the paper type.
Parameters:
paperType (int): The paper type.
Throws: LabelPrinterSDKException if an error occurs while sending the command.
void setPaperSpecifications(int dotLines, int width, int height, int gap) throws LabelPrinterSDKException
Description: Sets the paper specifications.
Parameters:
dotLines (int): Number of dot lines.
width (int): Width in dots.
height (int): Height in dots.
gap (int): Gap in dots.
Throws: LabelPrinterSDKException if an error occurs while sending the command.
void graphicPrint(byte[] data) throws LabelPrinterSDKException
Description: Prints a graphic.
Parameters:
data (byte[]): The graphic data.
Throws: LabelPrinterSDKException if an error occurs while sending the command.
void movePrintingPosition(int x, int y) throws LabelPrinterSDKException
Description: Moves the printing position.
Parameters:
x (int): The X coordinate.
y (int): The Y coordinate.
Throws: LabelPrinterSDKException if an error occurs while sending the command.
byte[] queryPrinterStatus() throws LabelPrinterSDKException
Description: Queries the printer status.
Returns:
byte[]: The printer status response.
Throws: LabelPrinterSDKException if an error occurs while sending the command or reading the response.

````
# Examples
````
Setting Up Paper Type
java
Copy code
printer.setUpPaperType(2);
Setting Paper Specifications
java
Copy code
printer.setPaperSpecifications(356, 304, 576, 30);
Printing Graphics
java
Copy code
byte[] graphicData = new byte[] { (byte) 0x01, (byte) 0xFC, (byte) 0x07, (byte) 0xFF };
printer.graphicPrint(graphicData);
Moving the Printing Position
java
Copy code
printer.movePrintingPosition(100, 200);
Querying the Printer Status
java
Copy code
byte[] status = printer.queryPrinterStatus();
System.out.println(Arrays.toString(status));
Troubleshooting
Common Issues
Incomplete Commands

Ensure that all command bytes are sent correctly.
Connection Issues

Verify that the printer is connected and accessible via the network.
Printer Not Responding

Check the printer status and ensure it is not in an error state.
Example Error Handling
java
Copy code
try {
    printer.setUpPaperType(2);
} catch (LabelPrinterSDKException e) {
    System.err.println("Failed to set up paper type: " + e.getMessage());
}


Building the SDK
Using Gradle
Run the following command to build the JAR file:

sh
Copy code
./gradlew build
The JAR file will be located in the build/libs directory.

Using Maven
Run the following command to build the JAR file:

sh
Copy code
mvn package
The JAR file will be located in the target directory.

Conclusion
You have now created an SDK for the SR 20 label printer, implemented core functionalities, and documented the usage. This SDK can be distributed and used by developers to interact with the SR 20 label printer easily.
````

### Here is sample example to use this Library ####

To use the SDK, create an instance of LabelPrinterSDKSR20 by passing an OutputStream and InputStream connected to the printer
````
import com.microlan.labelprinter.LabelPrinterSDKSR20;
import com.microlan.labelprinter.LabelPrinterSDKException;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    try (Socket socket = new Socket("printer_ip", 9100);
OutputStream out = socket.getOutputStream();
    InputStream in = socket.getInputStream()) {
      LabelPrinterSDKSR20 printer = new LabelPrinterSDKSR20(out, in);
      printer.setUpPaperType(2);
      printer.setPaperSpecifications(356, 304, 576, 30);
      byte[] graphicData = new byte[] { (byte) 0x01, (byte) 0xFC, (byte) 0x07, (byte) 0xFF };
      printer.graphicPrint(graphicData);
      byte[] status = printer.queryPrinterStatus();
      System.out.println("Printer Status: " + java.util.Arrays.toString(status));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

````
