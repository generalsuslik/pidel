//package com.pidel;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pidel.controller.PizzaController;
//import com.pidel.entity.Pizza;
//import com.pidel.entity.PizzaSize;
//import com.pidel.service.PizzaService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PizzaController.class)
//@ContextConfiguration(classes = {PizzaController.class})
//public class PizzaControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private PizzaService pizzaService;
//
//    private Pizza testPizza;
//    private PizzaSize testPizzaSize;
//
//    @BeforeEach
//    void setUp() {
//        testPizzaSize = PizzaSize.builder()
//                .id(1L)
//                .size(25)
//                .pizzas(new ArrayList<>())
//                .build();
//
//        testPizza = Pizza.builder()
//                .id(1L)
//                .name("Biba")
//                .pizzaSize(testPizzaSize)
//                .description("Boba")
//                .kcal(250.0)
//                .protein(12.0)
//                .fat(9.0)
//                .price(299.99)
//                .ingredients(new ArrayList<>())
//                .build();
//    }
//
//    @Test
//    @WithMockUser
//    void getAllPizzas_ShouldReturnListOfPizzas() throws Exception {
//        List<Pizza> pizzas = List.of(testPizza);
//        when(pizzaService.findAll()).thenReturn(pizzas);
//
//        mockMvc.perform(get("/pizza"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name").value("Biba"))
//                .andExpect(jsonPath("$[0].price").value(299.99))
//                .andExpect(jsonPath("$[0].kcal").value(250.0));
//
//        verify(pizzaService, times(1)).findAll();
//    }
//
//    @Test
//    @WithMockUser
//    void getPizza_WhenPizzaExists_ShouldReturnPizza() throws Exception {
//        when(pizzaService.findById(1L)).thenReturn(testPizza);
//
//        mockMvc.perform(get("/pizza/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Biba"))
//                .andExpect(jsonPath("$.price").value(299.99))
//                .andExpect(jsonPath("$.kcal").value(250.0));
//
//        verify(pizzaService, times(1)).findById(1L);
//    }
//
//    @Test
//    @WithMockUser
//    void createPizza_WithValidData_ShouldCreatePizza() throws Exception {
//        when(pizzaService.createPizza(any(Pizza.class))).thenReturn(testPizza);
//
//        mockMvc.perform(post("/pizza")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(testPizza)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Biba"))
//                .andExpect(jsonPath("$.price").value(299.99))
//                .andExpect(jsonPath("$.kcal").value(250.0));
//
//        verify(pizzaService, times(1)).createPizza(any(Pizza.class));
//    }
//
//    @Test
//    @WithMockUser
//    void updatePizza_WithValidData_ShouldUpdatePizza() throws Exception {
//        Pizza updatedPizza = Pizza.builder()
//                .id(1L)
//                .name("Boba")
//                .pizzaSize(testPizzaSize)
//                .price(349.99)
//                .description("Biba")
//                .kcal(260.0)
//                .protein(13.0)
//                .fat(10.0)
//                .ingredients(new ArrayList<>())
//                .build();
//
//        when(pizzaService.updatePizza(eq(1L), any(Pizza.class))).thenReturn(updatedPizza);
//
//        mockMvc.perform(post("/pizza/update/1")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedPizza)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Boba"))
//                .andExpect(jsonPath("$.description").value("Biba"))
//                .andExpect(jsonPath("$.price").value(349.99));
//
//        verify(pizzaService, times(1)).updatePizza(eq(1L), any(Pizza.class));
//    }
//
//    @Test
//    @WithMockUser
//    void deletePizza_ShouldDeletePizza() throws Exception {
//        doNothing().when(pizzaService).deletePizza(1L);
//
//        mockMvc.perform(post("/pizza/delete/1")
//                        .with(csrf()))
//                .andExpect(status().isOk());
//
//        verify(pizzaService, times(1)).deletePizza(1L);
//    }
//}
