package com.huawei.rtc.metric;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PrometheusMetricFactory {

    private CollectorRegistry registry;

    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<String, Counter>();
    private final ConcurrentMap<String, Gauge> gauges = new ConcurrentHashMap<String, Gauge>();
    private final ConcurrentMap<String, Histogram> histograms = new ConcurrentHashMap<String, Histogram>();

    public PrometheusMetricFactory(CollectorRegistry registry) {
        this.registry = registry;
    }

    public Counter counter(String name) {
        String key = sanitizeName(name);
        counters.computeIfAbsent( key,  k ->
                Counter.build().name(k).help(k).register(registry)
        );
        return counters.get(key);
    }

    public Gauge gauge(String name) {
        String key = sanitizeName(name);
        gauges.computeIfAbsent( key,  k ->
                Gauge.build().name(k).help(k).register(registry)
        );
        return gauges.get(key);
    }

    public Histogram histogram(String name) {
        String key = sanitizeName(name);
        histograms.computeIfAbsent( key, k ->
                Histogram.build().name(k).help(k).register(registry)
        );
        return histograms.get(key);
    }

    private String sanitizeName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}