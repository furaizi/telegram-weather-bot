package bot.weather;

public class WeatherDTO {

    public String description;
    public double temperature;
    public double tempFeelsLike;
    public double pressure;
    public long humidity;
    public long cloudiness;
    public double windSpeed;


    public String text() {
        var pressureInMillimetersOfMercury = pressure * 0.75;

        return """
                На вулиці: %s
                Температура: %.2f °C
                Відчувається як: %.2f °C
                Тиск: %.2f мм рт. ст.
                Вологість: %d%
                Хмарність: %d%
                Швидкість вітру: %2.f м/с
                """.formatted(description, temperature, tempFeelsLike, pressureInMillimetersOfMercury,
                                humidity, cloudiness, windSpeed);
    }
}
