package ru.gb.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.gb.auth.dto.ClientDtoResponse;

@FeignClient(value = "coreService", url = "http://core-service:9090/api/v1/client")
public interface CoreClient {

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	ClientDtoResponse add(@RequestHeader("username") String username);

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	void delete(@RequestHeader("username") String username);
}
