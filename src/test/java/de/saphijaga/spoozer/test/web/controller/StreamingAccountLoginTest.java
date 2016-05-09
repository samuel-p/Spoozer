package de.saphijaga.spoozer.test.web.controller;

import de.saphijaga.spoozer.web.controller.SpotifyController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by samuel on 09.05.16.
 */
public class StreamingAccountLoginTest {
    @InjectMocks
    private SpotifyController spotifyController;

    private MockMvc spotifyMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        spotifyMock = standaloneSetup(spotifyController).setViewResolvers(BaseIntegration.viewResolver()).build();
    }

    @Test
    public void shouldForwardToIndexForRoot() throws Exception {
        spotifyMock.perform(get("/spotify/login")).andExpect(status().isOk()).andExpect(redirectedUrl("/templates/index.html"));
    }
}
