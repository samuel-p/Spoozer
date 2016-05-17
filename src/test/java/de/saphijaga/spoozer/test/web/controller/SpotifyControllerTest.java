package de.saphijaga.spoozer.test.web.controller;

import de.saphijaga.spoozer.service.spotify.SpotifyApi;
import de.saphijaga.spoozer.web.controller.SpotifyController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by samuel on 16.10.15.
 */
public class SpotifyControllerTest {
    @Mock
    private SpotifyApi api;
    @InjectMocks
    private SpotifyController controller;

    private MockMvc mock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mock = standaloneSetup(controller).setViewResolvers(BaseIntegration.viewResolver()).build();

        try {
            when(api.getLoginURL(any(), any())).thenReturn("spoozer.de/spotify");
        } catch (UnsupportedEncodingException e) {
        }
    }

    @Test
    public void shouldRedirectToApiUrl() throws Exception {
        mock.perform(get("/spotify/login")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("spoozer.de/spotify"));
    }
}