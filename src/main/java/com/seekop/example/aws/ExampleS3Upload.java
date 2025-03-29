package com.seekop.example.aws;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 *
 * @author ggress
 */
@WebServlet(name = "ExampleS3Upload", urlPatterns = {"/upload"})
public class ExampleS3Upload extends HttpServlet {

    // Método auxiliar para obtener los datos de carga
    private static byte[] getDummyData() {
        // Generar datos aleatorios para la carga
        byte[] datos = new byte[100 * 1024 * 1024]; // Tamaño de datos de carga de 100 MB
        new Random().nextBytes(datos);
        return datos;
    }

    private static void uploadToS3(S3Client s3Client, String bucketName, String key, byte[] data) throws AwsServiceException, SdkClientException, S3Exception {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        System.out.println("Uploading... " + key);
        s3Client.putObject(request, RequestBody.fromBytes(data));
        System.out.println("Uploaded: " + key);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accessKey = "AWS_ACCESS_KEY";
        String secretKey = "AWS_SECRET_KEY";
        String bucketName = "sicop-mdm";
        String key = "847/dummy_test_file.dump";
        
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>S3 Upload Example</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Probando carga de archivo a S3</h1>");

            try {

                AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

                S3Client s3Client = S3Client.builder()
                        .region(Region.US_WEST_1)
                        .credentialsProvider(StaticCredentialsProvider.create(credentials))
                        .build();

                byte[] exampleData = getDummyData();

                uploadToS3(s3Client, bucketName, key, exampleData);

                out.println("<p>Se subio un archivo dummy de prueba...</p>");
                out.println("<p><b>Bucket:</b> " + bucketName + "</p>");
                out.println("<p><b>Key:</b> " + key + "</p>");

            } catch (AwsServiceException | SdkClientException error) {
                System.out.println("Error al cargar archivo a S3: " + error.getMessage());
                out.println("<p> Error al cargar archivo a S3: " + error.getMessage() + "</p>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
