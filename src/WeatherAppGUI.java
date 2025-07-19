import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;
    private JLabel weatherConditionImage;
    private JLabel temperatureText;
    private JLabel weatherConditionDesc;
    private JLabel humidityText;
    private JLabel windspeedText;
    
    public WeatherAppGUI() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        
        // Simple dark background
        getContentPane().setBackground(new Color(30, 30, 40));
        
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Search field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(20, 20, 280, 40);
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchTextField.setBackground(new Color(50, 50, 60));
        searchTextField.setForeground(Color.WHITE);
        searchTextField.setCaretColor(Color.WHITE);
        searchTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(searchTextField);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(320, 20, 60, 40);
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        add(searchButton);

        // Weather image
        weatherConditionImage = new JLabel(loadImage("assets/cloudy.png"));
        weatherConditionImage.setBounds(100, 80, 200, 200);
        weatherConditionImage.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionImage);

        // Temperature text
        temperatureText = new JLabel("22°C");
        temperatureText.setBounds(0, 300, 400, 50);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 36));
        temperatureText.setForeground(Color.WHITE);
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // Weather condition description
        weatherConditionDesc = new JLabel("Partly Cloudy");
        weatherConditionDesc.setBounds(0, 350, 400, 30);
        weatherConditionDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        weatherConditionDesc.setForeground(new Color(200, 200, 200));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // Humidity
        JLabel humidityImage = new JLabel(loadImage("assets/humidity.png"));
        humidityImage.setBounds(50, 420, 50, 50);
        add(humidityImage);

        humidityText = new JLabel("Humidity: 65%");
        humidityText.setBounds(120, 430, 200, 30);
        humidityText.setFont(new Font("Arial", Font.PLAIN, 16));
        humidityText.setForeground(Color.WHITE);
        add(humidityText);

        // Windspeed
        JLabel windspeedImage = new JLabel(loadImage("assets/windspeed.png"));
        windspeedImage.setBounds(50, 480, 50, 50);
        add(windspeedImage);

        windspeedText = new JLabel("Wind: 12 km/h");
        windspeedText.setBounds(120, 490, 200, 30);
        windspeedText.setFont(new Font("Arial", Font.PLAIN, 16));
        windspeedText.setForeground(Color.WHITE);
        add(windspeedText);

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();
                if (userInput.trim().isEmpty()) return;

                weatherData = WeatherApp.getWeatherData(userInput);
                if (weatherData != null) {
                    // Update weather image
                    String weatherCondition = (String) weatherData.get("weather_condition");
                    switch (weatherCondition) {
                        case "Clear": weatherConditionImage.setIcon(loadImage("assets/clear.png")); break;
                        case "Cloudy": weatherConditionImage.setIcon(loadImage("assets/cloudy.png")); break;
                        case "Rain": weatherConditionImage.setIcon(loadImage("assets/rain.png")); break;
                        case "Snow": weatherConditionImage.setIcon(loadImage("assets/snow.png")); break;
                    }

                    // Update temperature
                    double temperature = (double) weatherData.get("temperature");
                    temperatureText.setText(String.format("%.1f°C", temperature));

                    // Update condition
                    weatherConditionDesc.setText(weatherCondition);

                    // Update humidity
                    long humidity = (long) weatherData.get("humidity");
                    humidityText.setText("Humidity: " + humidity + "%");

                    // Update windspeed
                    double windspeed = (double) weatherData.get("windspeed");
                    windspeedText.setText("Wind: " + String.format("%.1f", windspeed) + " km/h");
                }
            }
        });
    }

    private ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}