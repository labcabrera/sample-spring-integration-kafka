package org.lab.tariff.calculator.gateway;

public interface Constants {

	public interface Topics {
		String CalculationIn = "tf-calculator-in";
		String CalculationOut = "tf-calculator-out";
	}

	public interface Channels {
		String CalculationIn = "channel-tf-calculator-in";
		String CalculationOut = "channel-tf-calculator-out";
		String CalculationErr = "channel-tf-calculator-err";
	}

	public interface MessageKeys {
		String CalculationMessageKey = "tf-calculator-message-key";
	}

	public interface Gateways {
		long CalculationResponseTimeout = 5000L;
	}

	public interface Rest {
		String CalculationRequestPath = "/api/v1/calculator";
	}

}
