package CSCI5308.GroupFormationTool.Response;

import CSCI5308.GroupFormationTool.Courses.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class ResponseControllerTest {

	@Mock
	private IResponsePersistence responsePersistence;
	@InjectMocks
	private ResponseController responseController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(responseController).build();
	}

	@Test
	public void loadQuestionsTest() throws Exception {
		this.mockMvc
				.perform(get("/response/takingsurvey").param("id", "121").with(csrf())
						.with(user("B-009911").password("Abcd@11").roles(Role.INSTRUCTOR.toString())))
				.andDo(print()).andExpect(model().attributeExists("courseId", "questionList"));
	}

	@Test
	public void submitSurveyTest() throws Exception {
		this.mockMvc.perform(get("/response/survey").param("id", "121").param("bannerID", "B00854211").with(csrf())
				.with(user("B00854211").password("Abcd@11").roles(Role.INSTRUCTOR.toString()))).andDo(print());
	}

}
