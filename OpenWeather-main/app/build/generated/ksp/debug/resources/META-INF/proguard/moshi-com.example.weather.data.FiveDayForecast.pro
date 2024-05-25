-if class com.example.weather.data.FiveDayForecast
-keepnames class com.example.weather.data.FiveDayForecast
-if class com.example.weather.data.FiveDayForecast
-keep class com.example.weather.data.FiveDayForecastJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
