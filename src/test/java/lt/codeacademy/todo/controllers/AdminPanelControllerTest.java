package lt.codeacademy.todo.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.UserUpdateRequest;
import lt.codeacademy.todo.entities.dto.responses.UserUpdateResponse;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@AutoConfigureMockMvc
@WithUserDetails("admin")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminPanelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getUser_returnUserSuccessfully() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/admin/panel/edit/{id}", 14L))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        UserDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);

        assertEquals(14L, user.getId());
    }

    @Test
    public void getUserList_returnUserListSuccessfully() throws Exception {
        MvcResult mvcResult =
                 mockMvc.perform(MockMvcRequestBuilders.get("/admin/panel/view"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        List<UserDTO> userDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<UserDTO>>(){});

        assertEquals(5, userDTOList.size());
    }

    @Test
    public void deleteUser_AssertFalseUserDoesExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/panel/view/{id}", 20L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(userRepository.existsById(20L));
    }

    @Test
    public void updateUser_AssertUpdatedFieldsReturnSuccessfully() throws Exception{

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("updatedFirstName");
        userUpdateRequest.setLastName("updatedLastName");
        userUpdateRequest.setAge(20);

        MvcResult mvcResult =
                 mockMvc.perform(MockMvcRequestBuilders.put("/admin/panel/edit/{id}", 20L)
                        .content(objectMapper.writeValueAsString(userUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        UserUpdateResponse userUpdateResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserUpdateResponse.class);

        assertEquals("updatedFirstName", userUpdateResponse.getFirstName());
        assertEquals("updatedLastName", userUpdateResponse.getLastName());
        assertEquals(20, userUpdateResponse.getAge());
    }

}
