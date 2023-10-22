package com.superindo.cashier.service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.model.TransactionDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfService {
	@Transactional
	public String generateInvoice(Transaction transaction) throws FileNotFoundException, MalformedURLException {

		String filePath = "src/main/resources/static/pdf/" + transaction.getTransactionNumber() + ".pdf";

		DecimalFormat df = new DecimalFormat("#,###");

		PdfWriter writer = new PdfWriter(filePath);

		PdfDocument pdf = new PdfDocument(writer);
		pdf.setDefaultPageSize(PageSize.A4);
		// Create a Document object
		Document document = new Document(pdf);

		String imagePath = "src/main/resources/static/images/Logo_Super_Indo.png";
		Image img = new Image(ImageDataFactory.create(imagePath));
		img.setWidth(70);
		img.setHeight(70);
		document.add(img);

		document.add(new Paragraph());
		document.add(new Paragraph("Invoice E-POST Superindo").setBold().setFontSize(14f));
		document.add(new Paragraph("Invoice ini merupakan bukti pembayaran yang sah, dan diterbitkan atas nama Partner:"));

		Table tableInvoiceIdentity = new Table(2);
		tableInvoiceIdentity.setBorder(Border.NO_BORDER);

		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm", new Locale("en", "US"));
		String formattedDate = sdf.format(transaction.getCreatedDate());

		tableInvoiceIdentity.addCell(new Cell().add(new Paragraph("Nama").setBorder(Border.NO_BORDER)));
		tableInvoiceIdentity
				.addCell(new Cell().add(new Paragraph(transaction.getCreatedUser()).setBorder(Border.NO_BORDER)));

		tableInvoiceIdentity.addCell(new Cell().add(new Paragraph("Tanggal").setBorder(Border.NO_BORDER)));
		tableInvoiceIdentity.addCell(new Cell().add(new Paragraph(formattedDate).setBorder(Border.NO_BORDER)));
		document.add(tableInvoiceIdentity);

		Set<TransactionDetail> transactionDetails = transaction.getTransactionDetails();
		Table tableTransactionDetail = new Table(4);
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("Nama").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("Harga").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("Qty").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("Sub-total").setBorder(Border.NO_BORDER)));
		for (TransactionDetail transactionDetail : transactionDetails) {
			String price = df.format(transactionDetail.getPrice());
			String qty = df.format(transactionDetail.getQty());
			String subtotal = df
					.format(transactionDetail.getPrice().multiply(BigDecimal.valueOf(transactionDetail.getQty())));

			tableTransactionDetail.addCell(
					new Cell().add(new Paragraph(transactionDetail.getProductVariant().getName()).setBorder(Border.NO_BORDER)));

			tableTransactionDetail.addCell(
					new Cell().add(new Paragraph(price).setBorder(Border.NO_BORDER)));

			tableTransactionDetail.addCell(
					new Cell().add(new Paragraph(qty).setBorder(Border.NO_BORDER)));

			tableTransactionDetail.addCell(
					new Cell().add(new Paragraph(subtotal).setBorder(Border.NO_BORDER)));

		}
		String totalAmmount = df.format(transaction.getTotalAmount());

		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph("").setBorder(Border.NO_BORDER)));
		tableTransactionDetail.addCell(
				new Cell().add(new Paragraph(totalAmmount).setBorder(Border.NO_BORDER)));

		document.add(new Paragraph(""));
		document.add(tableTransactionDetail);

		document.close();

		return filePath;
	}
}
