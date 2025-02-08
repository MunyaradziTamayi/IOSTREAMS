package com.app.timetable.Controller;

import com.codevirtus.Pesepay;
import com.codevirtus.payments.Payment;
import com.codevirtus.payments.Transaction;
import com.codevirtus.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PesepayController {

    private final Pesepay pesepay;

    public PesepayController() {
        // Replace with your integration key and encryption key
        String integrationKey ="";
        String encryptionKey ="";
        this.pesepay = new Pesepay(integrationKey, encryptionKey);

        // Set return and result URLs
        pesepay.setResultUrl("http://example.com/gateway/result");
        pesepay.setReturnUrl("http://example.com/gateway/return");
    }

    @GetMapping("/create-transaction")
    public String createTransaction(@RequestParam double amount, @RequestParam String currency, @RequestParam String reason) {
        Transaction transaction = pesepay.createTransaction(amount, currency, reason);
        Response response = pesepay.initiateTransaction(transaction);

        if (response.isSuccess()) {
            // Save the reference number and poll URL
            String referenceNumber = response.getReferenceNumber();
            String pollUrl = response.getPollUrl();

            // Redirect URL for the user to complete the transaction
            String redirectUrl = response.getRedirectUrl();
            return "Redirect URL: " + redirectUrl;
        } else {
            // Get error message
            return "Mirror: " + response.getMessage();
        }
    }

    @GetMapping("/make-seamless-payment")
    public String makeSeamlessPayment(@RequestParam String currencyCode, @RequestParam String paymentMethodCode, @RequestParam String customerEmail) {
        Payment payment = pesepay.createPayment(currencyCode, paymentMethodCode, customerEmail);

        // Create a Map of the required fields
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("requiredFieldKey", "requiredFieldValue");

        Response response = pesepay.makeSeamlessPayment(payment, "Online Payment", 1.0, requiredFields);

        if (response.isSuccess()) {
            // Save the reference number and poll URL
            String pollUrl = response.getPollUrl();
            String referenceNumber = response.getReferenceNumber();
            return "Payment successful. Poll URL: " + pollUrl + ", Reference Number: " + referenceNumber;
        } else {
            // Get error message
            return "Error: ";
        }
    }
}

