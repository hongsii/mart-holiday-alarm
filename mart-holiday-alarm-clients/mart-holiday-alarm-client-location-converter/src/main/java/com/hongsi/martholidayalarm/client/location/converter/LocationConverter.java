package com.hongsi.martholidayalarm.client.location.converter;


import java.util.function.Supplier;

public interface LocationConverter {

	LocationConversion convert(Supplier<String> querySupplier, Supplier<String> fallbackSupplier);
}
