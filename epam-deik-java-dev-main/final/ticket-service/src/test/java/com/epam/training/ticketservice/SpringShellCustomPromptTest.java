package com.epam.training.ticketservice;

import org.jline.utils.AttributedString;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringShellCustomPromptTest {

    SpringShellCustomPrompt springShellCustomPrompt = new SpringShellCustomPrompt();

    @Test
    void getPrompt() {
        Assert.assertEquals(new AttributedString("Ticket service> "),springShellCustomPrompt.getPrompt());
    }
}