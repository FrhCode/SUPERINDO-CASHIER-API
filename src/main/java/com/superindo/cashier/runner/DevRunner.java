package com.superindo.cashier.runner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.javafaker.Faker;
import com.superindo.cashier.model.Product;
import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.model.Role;
import com.superindo.cashier.model.TransactionDetail;
import com.superindo.cashier.model.User;
import com.superindo.cashier.repository.ProductCategoryRepository;
import com.superindo.cashier.repository.ProductRepository;
import com.superindo.cashier.repository.ProductVariantRepository;
import com.superindo.cashier.repository.RoleRepository;
import com.superindo.cashier.repository.UserRepository;
import com.superindo.cashier.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile({ "dev", "staging", "default" })
public class DevRunner implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final TransactionTemplate transactionTemplate;
	private final ProductCategoryRepository productCategoryRepository;
	private final ProductRepository productRepository;
	private final ProductVariantRepository productVariantRepository;
	private final TransactionService transactionService;

	@Override
	public void run(String... args) throws Exception {
		generateRole();
		generateUser();

		generateCategory();

		generateProduct();

		generateVariant();

		generateTransaction();
	}

	private void generateTransaction() {
		transactionTemplate.execute(transactionStatus -> {
			try {
				ProductVariant productVariant1 = productVariantRepository.findById(1L).get();
				ProductVariant productVariant2 = productVariantRepository.findById(2L).get();
				ProductVariant productVariant3 = productVariantRepository.findById(3L).get();

				TransactionDetail item1 = new TransactionDetail();
				item1.setProductVariant(productVariant1);
				item1.setQty(4L);
				item1.setPrice(productVariant1.getPrice());

				TransactionDetail item2 = new TransactionDetail();
				item2.setProductVariant(productVariant2);
				item2.setQty(10L);
				item2.setPrice(productVariant2.getPrice());

				TransactionDetail item3 = new TransactionDetail();
				item3.setProductVariant(productVariant3);
				item3.setQty(7L);
				item3.setPrice(productVariant3.getPrice());

				transactionService.save(List.of(item1, item2, item3));
				return null;
			} catch (Exception e) {
				transactionStatus.setRollbackOnly(); // Rollback transaction on exception
				System.out.println(e.getMessage());
				return null;
			}
		});
	}

	private void generateVariant() {
		Product indomie = productRepository.findByName("Indomie").get();

		ProductVariant indomie1 = new ProductVariant();
		indomie1.setProduct(indomie);
		indomie1.setCode("PDCT00000020001");
		indomie1.setName("Indomie Goreng Original");
		indomie1.setQty(1000L);
		indomie1.setPrice(new BigDecimal("3000"));
		indomie1.setActive(true);
		indomie1.setThumbnail("images/indomie-goreng.jpg");
		productVariantRepository.save(indomie1);

		ProductVariant indomie2 = new ProductVariant();
		indomie2.setProduct(indomie);
		indomie2.setCode("PDCT00000020002");
		indomie2.setName("Indomie Ayam Bawang");
		indomie2.setQty(500L);
		indomie2.setPrice(new BigDecimal("2700"));
		indomie2.setActive(true);
		indomie2.setThumbnail("images/indomie-ayam-bawang.png");
		productVariantRepository.save(indomie2);

		ProductVariant indomie3 = new ProductVariant();
		indomie3.setProduct(indomie);
		indomie3.setCode("PDCT00000020003");
		indomie3.setName("Indomie Goreng Aceh");
		indomie3.setQty(1000L);
		indomie3.setPrice(new BigDecimal("3200"));
		indomie3.setActive(true);
		indomie3.setThumbnail("images/indomie-goreng-aceh.jpg");
		productVariantRepository.save(indomie3);
	}

	private void generateProduct() {
		ProductCategory foodCategory = productCategoryRepository.findByName("Makanan").get();
		ProductCategory drinkCategory = productCategoryRepository.findByName("Minuman").get();
		ProductCategory detergenCategory = productCategoryRepository.findByName("Detergen").get();

		Product cheetos = new Product();
		cheetos.setPlu("PDCT0000001");
		cheetos.setName("Cheetos");
		cheetos.setProductCategory(foodCategory);
		cheetos.setActive(true);
		cheetos.setThumbnail("images/GUEST_b9c6571e-95ba-4f20-bd17-e623f8d4ab7d.webp");
		productRepository.save(cheetos);

		Product indomie = new Product();
		indomie.setPlu("PDCT0000002");
		indomie.setName("Indomie");
		indomie.setProductCategory(foodCategory);
		indomie.setActive(true);
		indomie.setThumbnail("images/indomie-ayam-bawang.png");
		productRepository.save(indomie);

		Product mieGaga = new Product();
		mieGaga.setPlu("PDCT0000007");
		mieGaga.setName("Mie gaga");
		mieGaga.setProductCategory(foodCategory);
		mieGaga.setActive(true);
		mieGaga.setThumbnail("images/mie-gaga-1340218801.webp");
		productRepository.save(mieGaga);

		Product airMineralAqua = new Product();
		airMineralAqua.setPlu("PDCT0000002");
		airMineralAqua.setName("Air Mineral Aqua");
		airMineralAqua.setProductCategory(drinkCategory);
		airMineralAqua.setActive(true);
		airMineralAqua.setThumbnail("images/RDS_HTC_Products_481657_image_B608_422143147.jpg");
		productRepository.save(airMineralAqua);

		Product cola = new Product();
		cola.setPlu("PDCT0000050");
		cola.setName("Cola");
		cola.setProductCategory(drinkCategory);
		cola.setActive(true);
		cola.setThumbnail("images/GUEST_7819ee30-1f78-46ee-a21c-d2096f99ba42.jpeg");
		productRepository.save(cola);

		Product rinso = new Product();
		rinso.setPlu("PDCT0000050");
		rinso.setName("Rinso");
		rinso.setProductCategory(detergenCategory);
		rinso.setActive(true);
		rinso.setThumbnail("images/20071828_2.jpg");
		productRepository.save(rinso);

	}

	private void generateCategory() {
		ProductCategory foodCategory = new ProductCategory();
		foodCategory.setName("Makanan");
		foodCategory.setActive(true);
		productCategoryRepository.save(foodCategory);

		ProductCategory drinkCategory = new ProductCategory();
		drinkCategory.setName("Minuman");
		drinkCategory.setActive(true);
		productCategoryRepository.save(drinkCategory);

		ProductCategory detergentCategory = new ProductCategory();
		detergentCategory.setName("Detergen");
		detergentCategory.setActive(true);
		productCategoryRepository.save(detergentCategory);

		Faker faker = new Faker(new Locale("in-ID"));
		for (int index = 0; index < 0; index++) {
			String randomCategory = faker.commerce().department();
			ProductCategory tempCategory = new ProductCategory();
			tempCategory.setName(randomCategory);
			tempCategory.setActive(true);
			productCategoryRepository.save(tempCategory);
		}

	}

	private void generateRole() {
		Role administrator = new Role();
		administrator.setName("administrator");
		roleRepository.save(administrator);

		Role customer = new Role();
		customer.setName("customer");
		roleRepository.save(customer);

	}

	private void generateUser() {
		transactionTemplate.execute(transactionStatus -> {
			try {
				User user = new User();
				user.setName("Mohammad Farhan Fajrul Haq");
				user.setEmail("farhan7534031b@gmail.com");
				user.setPhoneNumber("082188513499");
				user.setPassword(passwordEncoder.encode("indonesia123B"));
				user.setProfileImage("images/james-person-1.jpg");

				Optional<Role> optionalRole = roleRepository.findByName("administrator");

				if (optionalRole.isPresent()) {
					user.addRole(optionalRole.get());
				}

				userRepository.save(user);
				return null;
			} catch (Exception e) {
				transactionStatus.setRollbackOnly(); // Rollback transaction on exception
				System.out.println(e.getMessage());
				return null;
			}
		});

	}

}
