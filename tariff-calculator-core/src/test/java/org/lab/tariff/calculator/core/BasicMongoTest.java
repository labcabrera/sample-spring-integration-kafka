package org.lab.tariff.calculator.core;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.tariff.calculator.core.repositories.CalculationSourceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore("Not a real unit test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicMongoTest {

	@Autowired
	private CalculationSourceDataRepository repository;

	@Test
	public void test() {
		repository.findAll();
	}

}
