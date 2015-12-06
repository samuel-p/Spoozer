package de.saphijaga.spoozer.test.web.controller;

import de.saphijaga.spoozer.web.controller.SpoozerController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by samuel on 16.10.15.
 */
public class SpoozerControllerTest {
    @InjectMocks
    private SpoozerController controller;

    private MockMvc mock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mock = standaloneSetup(controller).setViewResolvers(BaseIntegration.viewResolver()).build();
    }

    @Test
    public void shouldForwardToIndexForRoot() throws Exception {
        mock.perform(get("/")).andExpect(status().isOk()).andExpect(forwardedUrl("/templates/index.html"));
    }
}