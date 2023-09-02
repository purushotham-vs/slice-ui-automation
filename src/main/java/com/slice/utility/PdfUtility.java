package com.slice.utility;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class PdfUtility {
    public static void pullFile() {
        ShellCommandExecutor.executeCommands("adb pull /sdcard/Download");
    }

    public static void deleteFile(String fileName) {
        ShellCommandExecutor.executeCommands("adb shell rm -r sdcard/Download/"+fileName+".*");
    }

    public static void deleteAllFile(String filePath) {
        ShellCommandExecutor.executeCommands("adb shell rm -r "+filePath);
    }


    public static String getPdfFileData(String filePath,String fileName)  {
        File file = new File(filePath+fileName);
        PDDocument document;
        PDFTextStripper stripper;
        try {
            document = PDDocument.load(file);
            stripper = new PDFTextStripper();
            String stripperText=stripper.getText(document);
            document.close();
          return stripperText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void deleteDirectory(String filePath)
            throws IOException {
        File file = new File(filePath);
        FileUtils.deleteDirectory(file);
    }

    public static int getNumberOfPages(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = PDDocument.load(file);
        int pages = document.getNumberOfPages();
        document.close();
        return pages;
    }

    public static PDDocumentInformation getInfo(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = PDDocument.load(file);
        PDDocumentInformation info = document.getDocumentInformation();
        document.close();
        return info;
    }


    public static boolean isPasswordRequired(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = PDDocument.load(file);
        boolean isEncrypted = document.isEncrypted();
        document.close();
        return isEncrypted;
    }

}
