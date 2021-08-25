package lt.codeacademy.todo.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.todo.entities.dto.ToDoDTO;
import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.repositories.SignificanceRepository;
import lt.codeacademy.todo.repositories.ToDoRepository;
import lt.codeacademy.todo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@AutoConfigureMockMvc
@WithUserDetails("admin")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private SignificanceRepository significanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createToDoTest_ReturnCreatedSuccessfully() throws Exception {
        ToDoRequest toDoRequest = new ToDoRequest();
        toDoRequest.setOwnerId(14L);
        toDoRequest.setToDoText("Test");
        toDoRequest.setSignificance("Ordinary");
        toDoRequest.setDeadline(LocalDateTime.of(2021, Month.SEPTEMBER,10,20,00));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/workspace/{id}/todo", 14L)
                .content(objectMapper.writeValueAsString(toDoRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ToDoResponse toDoResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ToDoResponse.class);

        assertEquals(14L, toDoResponse.getOwnerId());
        assertEquals("Test", toDoResponse.getToDoText());
    }

    @Test
    public void updateToDoTest_ReturnCreatedSuccessfully() throws Exception {
        ToDoRequest toDoRequest = new ToDoRequest();
        toDoRequest.setOwnerId(14L);
        toDoRequest.setToDoText("UpdatedText");
        toDoRequest.setSignificance("Ordinary");
        toDoRequest.setDeadline(LocalDateTime.of(2021, Month.SEPTEMBER,10,20,00));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.put("/workspace/{id}/todo/{toDoId}", 14L, 101L)
                        .content(objectMapper.writeValueAsString(toDoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ToDoResponse toDoResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ToDoResponse.class);

        assertEquals("UpdatedText", toDoResponse.getToDoText());
    }

    @Test
    public void getAllUsersToDo_ReturnListSuccessfully() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/workspace/{id}/todo", 14L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ToDoDTO> toDoDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ToDoDTO>>(){});

        assertEquals(5, toDoDTOList.size());
    }

    @Test
    public void getAllUsersToDoToday_ReturnListSuccessfully() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/workspace/{id}/today", 14L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ToDoDTO> toDoDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ToDoDTO>>(){});

        assertEquals(3, toDoDTOList.size());
    }

    @Test
    public void getAllUsersToBySignificanceOrdinary_ReturnListSuccessfully() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/workspace/{id}/{significance}", 14L, "Ordinary"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ToDoDTO> toDoDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ToDoDTO>>(){});

        assertEquals(2, toDoDTOList.size());
    }

    @Test
    public void getAllUsersToBySignificanceImportant_ReturnListSuccessfully() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/workspace/{id}/{significance}", 14L, "Important"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ToDoDTO> toDoDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ToDoDTO>>(){});

        assertEquals(2, toDoDTOList.size());
    }

    @Test
    public void getAllUsersToBySignificanceUrgent_ReturnListSuccessfully() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/workspace/{id}/{significance}", 14L, "Urgent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ToDoDTO> toDoDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ToDoDTO>>(){});

        assertEquals(1, toDoDTOList.size());
    }

    @Test
    public void deleteToDo_AsertFalseToDoExists () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/workspace/{id}/todo/{toDoId}", 14L, 104L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(userRepository.existsById(104L));
    }

    @Test
    public void deleteOldToDo_AsertFalseToDoExists () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/workspace/{id}/todo", 14L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(userRepository.existsById(105L));
        assertFalse(userRepository.existsById(106L));
    }

    @Test
    public void getToDo_returnToDoSuccessfully() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/workspace/{id}/todo/{toDoId}", 14L, 100L))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        ToDoDTO toDoDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ToDoDTO.class);

        assertEquals(100L,toDoDTO.getId());
    }
}
