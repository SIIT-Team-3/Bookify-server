package rs.ac.uns.ftn.Bookify.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.service.AccommodationService;
import rs.ac.uns.ftn.Bookify.service.ImageService;
import rs.ac.uns.ftn.Bookify.service.UserService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = AccommodationController.class)
public class AccommodationControllerTest {

    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(roles = "GUEST")
    public void getTotalPriceTest() throws Exception {
        when(accommodationService.isAvailable(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(true);
        when(accommodationService.checkPersons(anyLong(), anyInt())).thenReturn(true);
        when(accommodationService.getTotalPrice(1L, LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 15), PricePer.ROOM, 2)).thenReturn(50.0);

        mockMvc.perform(get("/api/v1/accommodations/price")
                        .param("id", "1")
                        .param("begin", "12.10.2024")
                        .param("end", "15.10.2024")
                        .param("pricePer", "ROOM")
                        .param("persons", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.is(50.0)));

        verify(accommodationService).isAvailable(1L, LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 15));
        verify(accommodationService).checkPersons(1L, 2);
        verify(accommodationService).getTotalPrice(1L, LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 15), PricePer.ROOM, 2);
    }
}
