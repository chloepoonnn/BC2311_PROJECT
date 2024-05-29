package com.vtxlab.project.bc_stock_finnhub;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.service.FinnhubService;

@SpringBootTest
@AutoConfigureMockMvc
class FinnhubControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FinnhubService finnhubService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetQuote() throws Exception {
    // Mock data
    Quote quote = Quote.builder().c(100d).h(110d).l(90d).o(95d).pc(90d)
        .t(1632825000L).build();
    // Mock service response
    when(finnhubService.getQuote("AAPL")).thenReturn(quote);
    // Perform GET request and verify the response
    mockMvc.perform(get("/stock/finnhub/api/v1/quote?symbol=AAPL"))//
        .andExpect(status().isOk())//
        .andExpect(jsonPath("$.syscode").value("000000"))//
        .andExpect(jsonPath("$.message").value("OK"))//
        .andExpect(jsonPath("$.data.c").value(100d))//
        .andExpect(jsonPath("$.data.h").value(110d))//
        .andExpect(jsonPath("$.data.l").value(90d))//
        .andExpect(jsonPath("$.data.o").value(95d))//
        .andExpect(jsonPath("$.data.pc").value(90d))//
        .andExpect(jsonPath("$.data.t").value(1632825000L));
  }

  @Test
  void testGetProfile() throws Exception {
    // Mock data
    CompanyProfile companyProfile = CompanyProfile.builder()//
        .country("US")//
        .currency("USD")//
        .exchange("NASDAQ")//
        .ipo("1980-12-12")//
        .marketCapitalization(200000000000L)//
        .name("Apple Inc")//
        .phone("14089961010")//
        .shareOutstanding(10000000000L)//
        .ticker("AAPL")//
        .weburl("https://www.apple.com/")//
        .logo("https://logo.com")//
        .build();
    // Mock service response
    when(finnhubService.getProfile("AAPL")).thenReturn(companyProfile);
    // Perform GET request and verify the response
    mockMvc.perform(get("/stock/finnhub/api/v1/profile2?symbol=AAPL"))//
        .andExpect(status().isOk())//
        .andExpect(jsonPath("$.syscode").value("000000"))//
        .andExpect(jsonPath("$.message").value("OK"))//
        .andExpect(jsonPath("$.data.country").value("US"))//
        .andExpect(jsonPath("$.data.currency").value("USD"))//
        .andExpect(jsonPath("$.data.exchange").value("NASDAQ"))//
        .andExpect(jsonPath("$.data.ipo").value("1980-12-12"))//
        .andExpect(jsonPath("$.data.marketCapitalization").value(200000000000d))//
        .andExpect(jsonPath("$.data.name").value("Apple Inc"))//
        .andExpect(jsonPath("$.data.phone").value("14089961010"))//
        .andExpect(jsonPath("$.data.shareOutstanding").value(10000000000d))//
        .andExpect(jsonPath("$.data.ticker").value("AAPL"))//
        .andExpect(jsonPath("$.data.weburl").value("https://www.apple.com/"))//
        .andExpect(jsonPath("$.data.logo").value("https://logo.com"));
  }
}
