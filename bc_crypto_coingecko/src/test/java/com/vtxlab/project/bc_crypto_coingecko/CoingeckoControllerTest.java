// package com.vtxlab.project.bc_crypto_coingecko;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import java.time.OffsetDateTime;
// import java.util.List;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;
// import com.vtxlab.project.bc_crypto_coingecko.controller.impl.CoingeckoController;
// import com.vtxlab.project.bc_crypto_coingecko.exception.ApiResp;
// import com.vtxlab.project.bc_crypto_coingecko.exception.exceptionEnum.Syscode;
// import com.vtxlab.project.bc_crypto_coingecko.model.CoinMarketRespDto;
// import com.vtxlab.project.bc_crypto_coingecko.model.Coingecko;
// import com.vtxlab.project.bc_crypto_coingecko.model.Coingecko.Roi;
// import com.vtxlab.project.bc_crypto_coingecko.service.CoingeckoService;

// @WebMvcTest(CoingeckoController.class)
// public class CoingeckoControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @InjectMocks
//     private CoingeckoController controller;

//     @MockBean
//     private CoingeckoService coingeckoService;

//     @Mock
//     private ModelMapper modelMapper;

//     @Test
//     void testGetAllData() throws Exception {
//         // Mock data
//         Coingecko btc = Coingecko.builder().id("bitcoin").symbol("btc")
//                 .name("Bitcoin").currentPrice(50927.0)
//                 .image("https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400")
//                 .ath(69045).atl(67.81).roi(null).marketCap(0).marketCapRank(1)
//                 .fullyDilutedValuation(1069462395035L).totalVolume(15535231359L)
//                 .high24h(51918.0).low24h(50943.0)
//                 .priceChange24h(-705.1136963920508)
//                 .priceChangePercentage24h(-1.36566)
//                 .marketCapChange24h(-13796198176L)
//                 .marketCapChangePercentage24h(-1.3607)
//                 .circulatingSupply(19638193L).totalSupply(21000000L)
//                 .maxSupply(21000000L).athChangePercentage(-26.2081)
//                 .athDate(OffsetDateTime
//                         .parse("2021-11-10T14:24:11.849Z").toLocalDateTime())
//                 .atlChangePercentage(75036.7016)
//                 .atlDate(OffsetDateTime.parse("2013-07-06T00:00:00Z")
//                         .toLocalDateTime())
//                 .lastUpdated(OffsetDateTime.parse("2024-02-26T10:00:17.351Z")
//                         .toLocalDateTime())
//                 .build();

//         Coingecko eth = Coingecko.builder().id("ethereum").symbol("eth")
//                 .name("Ethereum").currentPrice(3040.9)
//                 .image("https://assets.coingecko.com/coins/images/279/large/ethereum.png?1696501628")
//                 .ath(4878).atl(0.432979)
//                 .roi(Roi.builder().times(78.81877633594077).currency("btc")
//                         .percentage(7881.877633594077).build())
//                 .marketCap(0).marketCapRank(2)
//                 .fullyDilutedValuation(365779772593L).totalVolume(14997783942L)
//                 .high24h(3125.61).low24h(3019.51).priceChange24h(21.39)
//                 .priceChangePercentage24h(0.70854)
//                 .marketCapChange24h(2814406389L)
//                 .marketCapChangePercentage24h(0.77539)
//                 .circulatingSupply(120155437L).totalSupply(120155437L)
//                 .maxSupply(0).athChangePercentage(-37.63627)
//                 .athDate(OffsetDateTime
//                         .parse("2021-11-10T14:24:19.604Z").toLocalDateTime())
//                 .atlChangePercentage(702535.926)
//                 .atlDate(OffsetDateTime.parse("2015-10-20T00:00:00Z")
//                         .toLocalDateTime())
//                 .lastUpdated(OffsetDateTime.parse("2024-02-26T10:00:41.234Z")
//                         .toLocalDateTime())
//                 .build();

//         List<Coingecko> testData = List.of(btc, eth);

//         // Mock service response
//         when(coingeckoService.getDataFromApi("usd", "btc,eth"))
//                 .thenReturn(testData);

//         // Perform the request and validate the response
//         mockMvc.perform(
//                 get("/crypto/coingecko/api/v1?currency=usd&ids=btc,eth"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.syscode").value(Syscode.OK.getSyscode()))
//                 .andExpect(jsonPath("$.message").value(Syscode.OK.getMessage()))
//                 .andExpect(jsonPath("$.data").isArray())
//                 .andExpect(jsonPath("$.data[0].id").value("bitcoin"))
//                 .andExpect(jsonPath("$.data[0].symbol").value("btc"))
//                 .andExpect(jsonPath("$.data[0].name").value("Bitcoin"))
//                 .andExpect(jsonPath("$.data[0].currentPrice").value(50927.0))
//                 // Add more assertions for other fields as needed
//                 .andExpect(jsonPath("$.data[1].id").value("ethereum"))
//                 .andExpect(jsonPath("$.data[1].symbol").value("eth"))
//                 .andExpect(jsonPath("$.data[1].name").value("Ethereum"))
//                 .andExpect(jsonPath("$.data[1].currentPrice").value(3040.9));

//         // Call the controller method
//         ApiResp<List<Coingecko>> response =
//                 controller.getAllData("usd", "btc,eth");
//         ResponseEntity<ApiResp<List<Coingecko>>> responseEntity =
//                 ResponseEntity.ok(response);

//         // Verify the response
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(Syscode.OK.getSyscode(), response.getSyscode());
//         assertEquals(Syscode.OK.getMessage(), response.getMessage());
//         assertEquals(testData, response.getData());
//     }

//     @Test
//     void testGetCoinList() throws Exception {
//         // Mock data
//         List<String> coinList = List.of("btc", "eth", "xrp");

//         // Mock service response
//         when(coingeckoService.getCoinList()).thenReturn(coinList);

//         // Perform the request and validate the response
//         mockMvc.perform(get("/crypto/coingecko/api/v1/coin-list"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.syscode").value(Syscode.OK.getSyscode()))
//                 .andExpect(jsonPath("$.message").value(Syscode.OK.getMessage()))
//                 .andExpect(jsonPath("$.data").isArray())
//                 .andExpect(jsonPath("$.data[0]").value("btc"))
//                 .andExpect(jsonPath("$.data[1]").value("eth"))
//                 .andExpect(jsonPath("$.data[2]").value("xrp"));

//         // Call the controller method
//         ApiResp<List<String>> response = controller.getCoinList();
//         ResponseEntity<ApiResp<List<String>>> responseEntity =
//                 ResponseEntity.ok(response);

//         // Verify the response
//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(Syscode.OK.getSyscode(), response.getSyscode());
//         assertEquals(Syscode.OK.getMessage(), response.getMessage());
//         assertEquals(coinList, response.getData());
//     }

//     @Test
//     void testGetMarketData() throws Exception {
//         // Mock data
//         List<Coingecko> coinMarketData = List.of(
//                 Coingecko.builder().id("bitcoin").symbol("btc").name("Bitcoin")
//                         .build(),
//                 Coingecko.builder().id("ethereum").symbol("eth")
//                         .name("Ethereum").build());

//         List<CoinMarketRespDto> expectedResponse =
//                 List.of(new CoinMarketRespDto("bitcoin", "btc", "Bitcoin"),
//                         new CoinMarketRespDto("ethereum", "eth", "Ethereum"));

//         // Mock service response
//         when(coingeckoService.getCoinMarket()).thenReturn(coinMarketData);
//         when(modelMapper.map(coinMarketData.get(0), CoinMarketRespDto.class))
//                 .thenReturn(expectedResponse.get(0));
//         when(modelMapper.map(coinMarketData.get(1), CoinMarketRespDto.class))
//                 .thenReturn(expectedResponse.get(1));

//         // Perform the request and validate the response
//         mockMvc.perform(get("/crypto/coingecko/api/v1/market-data"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value("bitcoin"))
//                 .andExpect(jsonPath("$[0].symbol").value("btc"))
//                 .andExpect(jsonPath("$[0].name").value("Bitcoin"))
//                 .andExpect(jsonPath("$[1].id").value("ethereum"))
//                 .andExpect(jsonPath("$[1].symbol").value("eth"))
//                 .andExpect(jsonPath("$[1].name").value("Ethereum"));

//         // Call the controller method
//         List<CoinMarketRespDto> response = controller.getMarketData();

//         // Verify the response
//         assertEquals(expectedResponse, response);
//         verify(coingeckoService).getCoinMarket();
//         verify(modelMapper).map(coinMarketData.get(0), CoinMarketRespDto.class);
//         verify(modelMapper).map(coinMarketData.get(1), CoinMarketRespDto.class);
//     }
// }
