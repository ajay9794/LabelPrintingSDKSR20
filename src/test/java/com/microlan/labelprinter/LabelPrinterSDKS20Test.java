package com.microlan.labelprinter;


import org.junit.jupiter.api.Test;
import org.microlan.LabelPrinterSDKSR20;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LabelPrinterSDKS20Test {

    @Test
    public void testSetUpPaperType() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        InputStream in = new ByteArrayInputStream(new byte[] {});
        LabelPrinterSDKSR20 printer = new LabelPrinterSDKSR20(out, in);

        printer.setUpPaperType(2);

        byte[] expectedCommand = new byte[] { 0x1C, 0x25, 2 };
        assertArrayEquals(expectedCommand, ((ByteArrayOutputStream) out).toByteArray());
    }
}

