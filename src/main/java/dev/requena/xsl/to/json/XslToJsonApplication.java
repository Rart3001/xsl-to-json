package dev.requena.xsl.to.json;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XslToJsonApplication implements CommandLineRunner {

  private final GenerateService service;

  /**
   * Constructor
   *
   * @param service
   */
  public XslToJsonApplication(GenerateService service) {
    super();
    this.service = service;
  }

  public static void main(String[] args) {
    SpringApplication.run(XslToJsonApplication.class, args);
  }

  /* (non-Javadoc)
   * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
   */
  @Override
  public void run(String... args) throws Exception {
      service.generate();
  }
}
