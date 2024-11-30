package ru.moscow.tms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.moscow.tms.auth.models.Role;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.RoleRepository;
import ru.moscow.tms.auth.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static ru.moscow.tms.auth.security.AuthUtil.SUPERUSER_ROLE;

@SpringBootApplication
@EnableTransactionManagement
public class TmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmsApplication.class, args);
	}

	public static void test() {
		List<Integer> list = Arrays.asList(1,4,5,6,22,24,120);
		Optional<Integer> sum = list.stream().reduce(Integer::sum);
		System.out.println(sum.get());

		var res = list.stream().mapToInt(x->x).average();
		System.out.println(res);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository repository, RoleRepository roleRepository, PasswordEncoder encoder) {
		return args -> {
			UserEntity superuser = new UserEntity();
			Role role = roleRepository.findByName(SUPERUSER_ROLE).orElseThrow(() -> new IllegalStateException("Super role not found"));
			superuser.setUsername("admin");
			superuser.setPassword(encoder.encode("admin")); // generate random password and save it to file, let devops fetch it and change to some secure one
			superuser.setRoles(Collections.singletonList(role));
			superuser.setRoles(List.of(role));
			if (!repository.existsByUsername(superuser.getUsername())) {
				repository.save(superuser);
			}
		};
	}

}
