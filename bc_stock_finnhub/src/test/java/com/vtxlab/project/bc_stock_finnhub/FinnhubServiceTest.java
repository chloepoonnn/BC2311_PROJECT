package com.vtxlab.project.bc_stock_finnhub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.service.impl.FinnhubServiceImpl;

public class FinnhubServiceTest {
  @InjectMocks
  private FinnhubServiceImpl finnhubService;

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private UriComponentsBuilder finnhubQuoteUriBuilder;

  @Mock
  private UriComponentsBuilder finnhubCompanyProfileUriBuilder;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    // Mock behavior of finnhubQuoteUriBuilder and finnhubCompanyProfileUriBuilder
    when(finnhubQuoteUriBuilder.toUriString()).thenReturn(
        "https://finnhub.io/api/v1/quote?symbol=AAPL&token=cju3it9r01qr958213c0cju3it9r01qr958213cg");
    when(finnhubCompanyProfileUriBuilder.toUriString()).thenReturn(
        "https://finnhub.io/api/v1/stock/profile2?symbol=AAPL&token=cju3it9r01qr958213c0cju3it9r01qr958213cg");
  }

  @Test
  void testGetQuote() {
    // Mock data
    Quote expectedQuote = Quote.builder().c(100d).d(100d).dp(100d).h(110d)
        .l(90d).o(95d).pc(90d).t(1632825000L).build();
    // Mock behavior of getQuoteFromApi method
    when(finnhubService.getQuoteFromApi("AAPL")).thenReturn(expectedQuote);

    // Call the service method
    Quote actualQuote = finnhubService.getQuote("AAPL");
    // Verify the response
    assertEquals(expectedQuote, actualQuote);
  }

  @Test
  void testGetProfile() {
    // Mock data
    CompanyProfile expectedProfile = CompanyProfile.builder()//
        .country("US")//
        .currency("USD")//
        .exchange("NASDAQ")//
        .finnhubIndustry("Technology")//
        .ipo("1980-12-12")//
        .logo("https://finnhub.io/api/logo/AAPL.png")//
        .marketCapitalization(2000000000000L)//
        .name("Apple Inc")//
        .phone("14089961010")//
        .shareOutstanding(10000000000L)//
        .ticker("AAPL")//
        .weburl("https://www.apple.com/")//
        .build();
    // Mock behavior of getQuoteFromApi method
    when(finnhubService.getProfileFromApi("AAPL")).thenReturn(expectedProfile);
    // Call the service method
    CompanyProfile actualProfile = finnhubService.getProfile("AAPL");
    // Verify the response
    assertEquals(expectedProfile, actualProfile);

  }
}
