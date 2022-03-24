package ru.sbt.azatakhunov;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ApplicationLifecycle {
    void onStart(@Observes StartupEvent event) {
    }

    void onStop(@Observes ShutdownEvent event) {
    }
}