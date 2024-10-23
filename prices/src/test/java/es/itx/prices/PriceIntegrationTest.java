package es.itx.prices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import es.itx.prices.model.Price;
import es.itx.prices.repository.PriceRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        priceRepository.deleteAll();

        // Insertar datos de prueba
        priceRepository.save(new Price(1L, 35455L,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"), 1, 0, 35.50, "EUR"));

        priceRepository.save(new Price(1L, 35455L,
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"), 2, 1, 25.45, "EUR"));

        priceRepository.save(new Price(1L, 35455L,
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"), 3, 1, 30.50, "EUR"));

        priceRepository.save(new Price(1L, 35455L,
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"), 4, 1, 38.95, "EUR"));
    }
    @Test
    void testGetPriceAt10amOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":35455,\"brandId\":1,\"priceList\":1,\"startDate\":\"2020-06-14T00:00:00\",\"endDate\":\"2020-12-31T23:59:59\",\"price\":35.50}"));
    }

    @Test
    void testGetPriceAt4pmOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("date", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":35455,\"brandId\":1,\"priceList\":2,\"startDate\":\"2020-06-14T15:00:00\",\"endDate\":\"2020-06-14T18:30:00\",\"price\":25.45}"));
    }

    @Test
    void testGetPriceAt10amOnJune15() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("date", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":35455,\"brandId\":1,\"priceList\":3,\"startDate\":\"2020-06-15T00:00:00\",\"endDate\":\"2020-06-15T11:00:00\",\"price\":30.50}"));
    }
    
    @Test
    void testNoPriceFound() throws Exception {
        // Test para un caso donde no debería haber precio (fuera de rango)
        mockMvc.perform(get("/api/prices")
                .param("date", "2021-06-14T10:00:00")  // Fecha fuera del rango de datos
                .param("productId", "35455")
                .param("brandId", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Espera que lance una excepción o código 404
                .andExpect(content().string("No price found for the given criteria."));
    }
}