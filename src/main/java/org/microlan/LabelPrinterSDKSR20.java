package org.microlan;
import java.io.OutputStream;
import java.io.IOException;

import java.io.InputStream;

public class LabelPrinterSDKSR20 {
    private OutputStream printerOutput;
    private InputStream printerInput;

    public LabelPrinterSDKSR20(OutputStream printerOutput, InputStream printerInput) {
        this.printerOutput = printerOutput;
        this.printerInput = printerInput;
    }

    public void setUpPaperType(int paperType) throws LabelPrinterSDKException {
        byte[] command = new byte[] { 0x1C, 0x25, (byte) paperType };
        sendCommand(command);
    }

    public void setPaperSpecifications(int dotLines, int width, int height, int gap) throws LabelPrinterSDKException {
        byte l0 = (byte) (dotLines & 0xFF);
        byte l1 = (byte) ((dotLines >> 8) & 0xFF);
        byte wL = (byte) (width & 0xFF);
        byte wH = (byte) ((width >> 8) & 0xFF);
        byte hL = (byte) (height & 0xFF);
        byte hH = (byte) ((height >> 8) & 0xFF);
        byte[] command = new byte[] { 0x1B, 0x29, l0, l1, wL, wH, hL, hH, (byte) gap };
        sendCommand(command);
    }

    public void graphicPrint(byte[] data) throws LabelPrinterSDKException {
        byte[] header = new byte[] { 0x1B, 0x19, 1, (byte) data.length, 0 };
        byte[] command = new byte[header.length + data.length];
        System.arraycopy(header, 0, command, 0, header.length);
        System.arraycopy(data, 0, command, header.length, data.length);
        sendCommand(command);
    }

    public void moveToNextTab() throws LabelPrinterSDKException {
        byte[] command = new byte[] { 0x1B, 0x69 };
        sendCommand(command);
    }

    public void movePrintingPosition(int x, int y) throws LabelPrinterSDKException {
        byte[] commandX = new byte[] { 0x1B, 0x34, (byte) x, (byte) (x >> 8) };
        byte[] commandY = new byte[] { 0x1B, 0x35, (byte) y, (byte) (y >> 8) };
        sendCommand(commandX);
        sendCommand(commandY);
    }

    public byte[] queryPrinterStatus() throws LabelPrinterSDKException {
        byte[] command = new byte[] { 0x1B, 0x76 };
        sendCommand(command);
        byte[] response = new byte[10]; // Assuming the response is 10 bytes long
        try {
            int bytesRead = printerInput.read(response);
            if (bytesRead != 10) {
                throw new LabelPrinterSDKException("Incomplete response from printer");
            }
        } catch (IOException e) {
            throw new LabelPrinterSDKException("Failed to read printer status", e);
        }
        return response;
    }

    private void sendCommand(byte[] command) throws LabelPrinterSDKException {
        try {
            printerOutput.write(command);
            printerOutput.flush();
        } catch (IOException e) {
            throw new LabelPrinterSDKException("Failed to send command to printer", e);
        }
    }
}
