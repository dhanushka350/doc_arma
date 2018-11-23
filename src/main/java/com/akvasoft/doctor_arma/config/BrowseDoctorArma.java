package com.akvasoft.doctor_arma.config;

import com.ImageTypers.ImageTypersAPI;
import com.akvasoft.doctor_arma.common.DateTimeUtil;
import com.akvasoft.doctor_arma.modal.BrowseDoctorArmaModel;
import com.akvasoft.doctor_arma.modal.BrowseDoctorArmaModel2;
import com.akvasoft.doctor_arma.modal.BrowseDoctorArmaModel3;
import com.akvasoft.doctor_arma.modal.ReplaceCharacters;
import com.akvasoft.doctor_arma.repo.Doctor;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class BrowseDoctorArma implements InitializingBean {
    private static FirefoxDriver driver = null;
    private static String url[] = {"https://gss.sgk.gov.tr/OzelSHSBilgi/pages/doktorArama.faces?fbclid=IwAR1ZyTAxXu9yQdv_KGWMEWUjjHzjL2lKp53QxeVSI-Aqmm0N5FrWkuBcTlg"};
    private static String codes[] = {"CALCIO"};
    private static HashMap<String, String> handlers = new HashMap<>();
    boolean newRow = true;
    @Autowired
    private Doctor doctor;
    int mainRowCount = 0;
    int rowCountOne = 0;
    int rowCountTwo = 0;
    String innerText = "";
    String innerText2 = "";
    private String one = "";
    private String two = "";

    public void initialise() throws Exception {


        System.setProperty("webdriver.gecko.driver", "/var/lib/tomcat8/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        driver = new FirefoxDriver(options);

        for (int i = 0; i < url.length - 1; i++) {
            driver.executeScript("window.open()");
        }

        ArrayList<String> windowsHandles = new ArrayList<>(driver.getWindowHandles());

        for (int i = 0; i < url.length; i++) {
            handlers.put(codes[i], windowsHandles.get(i));
        }
        List<BrowseDoctorArmaModel2> dataList = new ArrayList<>();

        while (true) {
            try {
                dataList = browseDoctorArmaModel2s();
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.err.println("finished..");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("RESTARTING------");
                break;

            }
        }

        Workbook workbook = new XSSFWorkbook();
        //createXlsFileInfoBetFootball(dataList, "Doctor Arama", workbook);
        new File("/var/lib/tomcat8/current").mkdir();
        new File("/var/lib/tomcat8/history/doc").mkdir();

        Calendar cal = Calendar.getInstance();
        FileOutputStream fileOut = new FileOutputStream("/var/lib/tomcat8/current/doc.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        fileOut = new FileOutputStream("/var/lib/tomcat8/history/doc/doc" + DateTimeUtil.getCurrentTimeStamp() + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();


    }


    private List<BrowseDoctorArmaModel2> browseDoctorArmaModel2s() throws Exception {
        List<BrowseDoctorArmaModel2> list = new ArrayList<>();
        String currentCityName = "";
        String currentBranchName = "";
        ReplaceCharacters characters = new ReplaceCharacters();
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        driver.get("https://gss.sgk.gov.tr/OzelSHSBilgi/pages/doktorArama.faces?fbclid=IwAR1ZyTAxXu9yQdv_KGWMEWUjjHzjL2lKp53QxeVSI-Aqmm0N5FrWkuBcTlg");
        driver.findElementByXPath("//*[@id=\"form1:tabbedPanel1tabbedPanelhyperlink_2\"]").click();
        List<WebElement> combo1 = driver.findElementByXPath("//*[@id=\"form1:cmbIl\"]").findElements(By.xpath("./*"));
        List<WebElement> combo2 = driver.findElementByXPath("//*[@id=\"form1:menu1\"]").findElements(By.xpath("./*"));
        int combo1Size = combo1.size();
        int combo2Size = combo2.size();
        boolean isChangeComboOne = false;
        boolean isChangeComboTwo = false;

        System.out.println("checking last saved point...");
        for (BrowseDoctorArmaModel2 model2 : doctor.findAllByOrderByIdDesc()) {
            if (model2.getAcademicTitle().equalsIgnoreCase("search with")) {
                one = model2.getCity();
                two = model2.getBranch();
                System.err.println("__________________________________________________");
                System.out.println("______________________FOUND_______________________");
                System.out.println(one + "-" + two);
                System.err.println("__________________________________________________");
                break;
            }

        }
        System.out.println("checking last enter of city..");
        for (WebElement element : combo1) {
            if (characters.replace(element.getAttribute("innerText")).equals(one)) {
                System.out.println(characters.replace(element.getAttribute("innerText")) + " is equal to " + one);
                isChangeComboOne = true;
                break;
            }
            rowCountOne++;
        }

        System.out.println("checking last enter of branch..");
        for (WebElement element : combo2) {
            if (characters.replace(element.getAttribute("innerText")).equals(two)) {
                System.out.println(characters.replace(element.getAttribute("innerText")) + " is equal to " + two);
                isChangeComboTwo = true;
                break;
            }
            rowCountTwo++;
        }

        if (!isChangeComboOne) {
            rowCountOne = 0;
        }
        if (!isChangeComboTwo) {
            rowCountTwo = 0;
        }


        System.out.println("City index " + rowCountOne);
        System.out.println("Branch index " + rowCountTwo);

        for (int i = rowCountOne; i < combo1Size; i++) {

            if (i == 10) {
                break;
            }
            for (int j = rowCountTwo; j < combo2Size; j++) {
                Thread.sleep(1000);
                driver.findElementByXPath("//*[@id=\"form1:tabbedPanel1tabbedPanelhyperlink_2\"]").click();
                driver.findElementByXPath("//*[@id=\"form1:cmbIl\"]").click();
                driver.findElementByXPath("//*[@id=\"form1:menu1\"]").click();
                Thread.sleep(1000);
                WebElement currentCity = driver.findElementByXPath("//*[@id=\"form1:cmbIl\"]").findElements(By.xpath("./*")).get(i);
                WebElement currentBranch = driver.findElementByXPath("//*[@id=\"form1:menu1\"]").findElements(By.xpath("./*")).get(j);
                currentCity.click();
                currentBranch.click();

                currentCityName = characters.replace(currentCity.getAttribute("innerText"));
                currentBranchName = characters.replace(currentBranch.getAttribute("innerText"));

                if (currentBranchName.length() < 2) {
                    continue;
                }

                System.out.println("current city is --- " + currentCityName);
                System.out.println("current branch is --- " + currentBranchName);

                resolveCaptcha();

                boolean isCombinationAdded = false;
                System.out.println("_______________________________________CURRENT COMBINATION__________________________________________");
                System.out.println(currentCityName + "---" + characters.replace(currentBranchName));

                int lastRow = 0;
                for (BrowseDoctorArmaModel2 model2 : doctor.findAllByOrderByIdDesc()) {
                    if (model2.getAcademicTitle().equals("search with")) {
                        System.out.println(characters.replace(model2.getCity()) + "====================" + model2.getBranch());
                        System.out.println(characters.replace(model2.getCity()) + "====================" + characters.replace(currentBranchName));

                        if (characters.replace(model2.getCity()).equalsIgnoreCase(characters.replace(currentCityName))
                                && characters.replace(model2.getBranch()).equalsIgnoreCase(characters.replace(currentBranchName))) {
                            isCombinationAdded = true;
                            break;
                        }
                    }
                    lastRow++;
                }

                if (!isCombinationAdded) {
                    lastRow = 0;
                    setCurrentCombination(currentCityName, currentBranchName);
                }

                List<WebElement> mainList = null;
                Thread.sleep(1000);
                WebElement mainTable = driver.findElementByXPath("/html/body/form/center/div/div[2]/div[1]/table/tbody");

                try {
                    mainList = mainTable.findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
                } catch (Exception t) {
                    System.err.println("main table not found ... skipping...");
                    continue;
                }

                checkDuplicateAndWrite(mainList, currentCityName, currentBranchName, lastRow);


                if (rowCountTwo == 138) {
                    rowCountTwo = 0;
                }
            }


            if (i == 3) {
                break;
            }
        }


        return list;
    }

    private void checkDuplicateAndWrite(List<WebElement> mainList, String city, String branch, int lastRow) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        ReplaceCharacters characters = new ReplaceCharacters();
        for (int i = lastRow; i < mainList.size(); i++) {
            WebElement mainTable = driver.findElementByXPath("/html/body/form/center/div/div[2]/div[1]/table/tbody");
            mainList = mainTable.findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
            String atitle = characters.replace(mainList.get(i).findElements(By.tagName("td")).get(0).getAttribute("innerText"));
            String title = characters.replace(mainList.get(i).findElements(By.tagName("td")).get(1).getAttribute("innerText"));
            String name = characters.replace(mainList.get(i).findElements(By.tagName("td")).get(2).getAttribute("innerText"));
            String surname = characters.replace(mainList.get(i).findElements(By.tagName("td")).get(3).getAttribute("innerText"));
            System.out.println("Searching..");

            boolean isDuplicate = false;
//            try {
//                BrowseDoctorArmaModel2 duplicate = doctor.findAllByOrderByIdDesc();
//                if
//                (duplicate.getAcademicTitle().equalsIgnoreCase(atitle)
//                        && duplicate.getTitle().equalsIgnoreCase(title)
//                        && duplicate.getSurname().equalsIgnoreCase(surname)
//                        && duplicate.getBranch().equalsIgnoreCase(characters.replace(branch))
//                        && duplicate.getCity().equalsIgnoreCase(characters.replace(city))) {
//
//                    System.err.println("Skipping Duplicate Found in Main Page..");
//                    System.err.println(atitle + "---" + title + "---" + surname + "---" + name + "---" + city + "---" + branch);
//                    System.err.println(duplicate.getAcademicTitle() + "---" + duplicate.getTitle() + "---" + duplicate.getSurname() + "---" + duplicate.getName() + "---" + duplicate.getCity() + "---" + duplicate.getBranch());
//
//                    isDuplicate = true;
//
//                }
//
//                if (isDuplicate) {
//                    continue;
//                }
//
//            } catch (NullPointerException g) {
//            }

            System.out.println("No Duplicate Found..");
            String workspace = mainList.get(i).findElements(By.tagName("td")).get(4).getAttribute("innerText");
            jse.executeScript("arguments[0].click();", mainList.get(i).findElements(By.tagName("td")).get(4));
            boolean b = scrapeInnerPage(workspace, city, branch);


            if (!b) {
                driver.navigate().back();
                String currentUrl = driver.getCurrentUrl();
                driver.get(currentUrl);
                i = i - 1;
                System.err.println("Refreshing page_______*****************************");

                try {
                    mainList = driver.findElement(By.xpath("/html/body/form/center/div/div[2]/div[1]/table/tbody/tr/td/table/tbody/tr/td/table/tbody")).findElements(By.xpath("./*"));
                } catch (UnhandledAlertException e) {
                    Thread.sleep(10000);
                    try {
                        driver.switchTo().alert().accept();
                    } catch (NoAlertPresentException w) {
                        System.err.println("NoAlertPresentException__");
                    }
                }

                continue;
            }

        }

    }

    private void setCurrentCombination(String currentCity, String currentBranch) {
        BrowseDoctorArmaModel2 em = new BrowseDoctorArmaModel2();
        BrowseDoctorArmaModel3 emodel3 = new BrowseDoctorArmaModel3();
        BrowseDoctorArmaModel emodel = new BrowseDoctorArmaModel();
        List<BrowseDoctorArmaModel3> emodel3List = new ArrayList<>();
        List<BrowseDoctorArmaModel> emodelList = new ArrayList<>();
        em.setAcademicTitle("search with");
        em.setTitle(currentCity);
        em.setName("+");
        em.setSurname(currentBranch);
        em.setWorkplace("---");
        em.setNationality("---");
        em.setGender("---");
        em.setBirth_year("---");
        em.setPersonnel_type("---");
        em.setUndergraduate_school("---");
        em.setProffessional_area("---");
        em.setCity(currentCity);
        em.setBranch(currentBranch);

        emodel3.setName_of_the_institution("---");
        emodel3.setInstitution_city("---");
        emodel3.setStart_date("---");
        emodel3.setFinal_date("---");
        emodel3.setSubject_of_contract("---");
        emodel3.setDoc(em);
        emodel3List.add(emodel3);

        emodel.setBranş_Adı("---");
        emodel.setMezun_Olduğu_Okul("---");
        emodel.setDiploma_Tescil_Yılı("---");
        emodel.setPersonel(em);
        emodelList.add(emodel);

        em.setEduList(emodelList);
        em.setWorkplaces(emodel3List);
        doctor.save(em);
    }

    private boolean resolveCaptcha() throws Exception {
        ReplaceCharacters characters = new ReplaceCharacters();
        String access_token = "6FCD4FA636F442C7A33A4963BDBA9122";
        ImageTypersAPI c = new ImageTypersAPI(access_token);
        String balance = c.account_balance();
        String cptchaID = "";
        System.out.println(String.format("Balance: %s", balance));

        boolean isCaptchaWrong = true;
        while (isCaptchaWrong) {
            WebElement ele = driver.findElementByXPath("/html/body/form/center/div/div[2]/div[1]/div/table/tbody/tr[2]/td/div[2]/div/table/tbody/tr/td/table/tbody/tr[6]/td[2]/img");

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = null;

            fullImg = ImageIO.read(screenshot);

            Point point = ele.getLocation();

            int eleWidth = ele.getSize().getWidth();
            int eleHeight = ele.getSize().getHeight();

            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);

            File screenshotLocation = new File("/var/lib/tomcat8/history/doc/captcha.png");
            FileUtils.copyFile(screenshot, screenshotLocation);

            System.out.println("Waiting for captcha to be solved ...");
            String resp = c.solve_captcha("/var/lib/tomcat8/history/doc/captcha.png", true);
            cptchaID = c.captcha_id();

            System.out.println(String.format("Captcha text: %s", resp));

            WebElement captcha = driver.findElementByXPath("//*[@id=\"form1:guvenlikNumarasi2\"]");
            captcha.clear();
            captcha.sendKeys(new String[]{resp});

            try {
                driver.findElementByXPath("//*[@id=\"form1:btnDetayliDoktorAra\"]").click();
            } catch (UnhandledAlertException e) {
                driver.switchTo().alert().accept();
                Thread.sleep(10000);
            }


            String message = "";

            try {
                message = driver.findElementByXPath("//*[@id=\"form1:text5\"]").getAttribute("innerText");
                System.out.println(message + " 1");

                if (message.equals("Güvenlik Numarasını Yanlış Girdiniz...")) {
                    System.err.println("wrong captcha_________________________________________* " + characters.replace(message));
                    isCaptchaWrong = true;
                    c.set_captcha_bad(cptchaID);
                } else if (message.equals("Aradığınız kriterlere uygun veri bulunamadı.")) {
                    System.out.println("CAPTCHA IS OKKKK");
                    isCaptchaWrong = false;
                    System.err.println("No Data in Table____________________________________________* " + message);
                    continue;
                } else {
                    System.out.println("CAPTCHA IS OKKKK");
                    isCaptchaWrong = false;
                }

            } catch (NoSuchElementException l) {
                isCaptchaWrong = false;
            }

        }
        return false;
    }


    private boolean scrapeInnerPage(String workspace, String combo1, String combo2) throws InterruptedException {

        try {
            BrowseDoctorArmaModel2 m = null;
            List<BrowseDoctorArmaModel3> model3List = new ArrayList<>();
            List<BrowseDoctorArmaModel> modelList = new ArrayList<>();
            ReplaceCharacters characters = new ReplaceCharacters();

            System.out.println("ENTERED PAGE - " + driver.getCurrentUrl());
            Thread.sleep(3000); // 20 000
            m = new BrowseDoctorArmaModel2();
            WebElement table_one = driver.findElementByXPath("/html/body/center/div/div[2]/div[1]/form/table/tbody/tr[1]/td[1]/div[1]/table/tbody");
            String Akademik_Unvan = table_one.findElements(By.xpath("./*")).get(1).findElements(By.xpath("./*")).get(2).getAttribute("innerText");
            String Unvan = table_one.findElements(By.xpath("./*")).get(1).findElements(By.xpath("./*")).get(4).getAttribute("innerText");
            String Adı = table_one.findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
            String Soyadı = table_one.findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(3).getAttribute("innerText");
            String Uyruk = table_one.findElements(By.xpath("./*")).get(3).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
            String Cinsiyet = table_one.findElements(By.xpath("./*")).get(3).findElements(By.xpath("./*")).get(3).getAttribute("innerText");
            String Doğum_Yılı = table_one.findElements(By.xpath("./*")).get(4).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
            String Personel_Tipi = table_one.findElements(By.xpath("./*")).get(4).findElements(By.xpath("./*")).get(3).getAttribute("innerText");
            String Lisans_Okul = table_one.findElements(By.xpath("./*")).get(5).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
            String Mesleki_İlgi_Alanı = table_one.findElements(By.xpath("./*")).get(6).findElements(By.xpath("./*")).get(1).getAttribute("innerText");


            m.setAcademicTitle(characters.replace(Akademik_Unvan));
            m.setTitle(characters.replace(Unvan));
            m.setName(characters.replace(Adı));
            m.setSurname(characters.replace(Soyadı));
            m.setWorkplace(characters.replace(workspace));
            m.setNationality(characters.replace(Uyruk));
            m.setGender(characters.replace(Cinsiyet));
            m.setBirth_year(characters.replace(Doğum_Yılı));
            m.setPersonnel_type(characters.replace(Personel_Tipi));
            m.setUndergraduate_school(characters.replace(Lisans_Okul));
            m.setProffessional_area(characters.replace(Mesleki_İlgi_Alanı));
            m.setCity(combo1);
            m.setBranch(combo2);

            System.out.println("Akademik Unvan - " + Akademik_Unvan);
            System.out.println("Unvan          - " + Unvan);
            System.out.println("Adı            - " + Adı);
            System.out.println("Soyadı         - " + Soyadı);
            System.out.println("Uyruk          - " + Uyruk);
            System.out.println("Cinsiyet       - " + Cinsiyet);
            System.out.println("Doğum_Yılı     - " + Doğum_Yılı);
            System.out.println("Personel_Tipi  - " + Personel_Tipi);
            System.out.println("Lisans_Okul    - " + Lisans_Okul);
            System.out.println("Mesleki_İlgi_Alanı  - " + Mesleki_İlgi_Alanı);
            System.out.println("1 st table is complete_______________________________________________________________________***");


            WebElement table_two = driver.findElementByXPath("//*[@id=\"form1:tableEx2\"]");
            List<WebElement> rows = table_two.findElement(By.tagName("tbody")).findElements(By.xpath("./*"));
            BrowseDoctorArmaModel model = null;

            for (WebElement row : rows) {
                model = new BrowseDoctorArmaModel();
                String Branş_Adı = row.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                String Mezun_Olduğu_Okul = row.findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                String Diploma_Tescil_Yılı = row.findElements(By.xpath("./*")).get(2).getAttribute("innerText");

                model.setBranş_Adı(characters.replace(Branş_Adı));
                model.setMezun_Olduğu_Okul(characters.replace(Mezun_Olduğu_Okul));
                model.setDiploma_Tescil_Yılı(characters.replace(Diploma_Tescil_Yılı));
                model.setPersonel(m);
                modelList.add(model);

                System.out.println("Branş_Adı     - " + Branş_Adı);
                System.out.println("Mezun_Olduğu_Okul  - " + Mezun_Olduğu_Okul);
                System.out.println("Diploma_Tescil_Yılı    - " + Diploma_Tescil_Yılı);


            }
            System.out.println("2 st table is complete_______________________________________________________________________***");


            WebElement table_three = driver.findElementByXPath("/html/body/center/div/div[2]/div[1]/form/table/tbody/tr[1]/td[1]/div[3]/table/tbody/tr[2]/td/table/tbody");
            List<WebElement> brows = table_three.findElements(By.xpath("./*"));
            BrowseDoctorArmaModel3 model3 = null;

            for (WebElement brow : brows) {
                model3 = new BrowseDoctorArmaModel3();
                String Kurum_Adı = brow.findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                String Kurum_İli = brow.findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                String Başlangıç_Tarihi = brow.findElements(By.xpath("./*")).get(2).getAttribute("innerText");
                String Bitiş_Tarihi = brow.findElements(By.xpath("./*")).get(3).getAttribute("innerText");
                String Sözleşme_Konusu = brow.findElements(By.xpath("./*")).get(4).getAttribute("innerText");

                System.out.println("Kurum_Adı --- " + Kurum_Adı);
                System.out.println("Kurum_İli --- " + Kurum_İli);
                System.out.println("Başlangıç_Tarihi --- " + Başlangıç_Tarihi);
                System.out.println("Bitiş_Tarihi --- " + Bitiş_Tarihi);
                System.out.println("Sözleşme_Konusu --- " + Sözleşme_Konusu);

                model3.setName_of_the_institution(characters.replace(Kurum_Adı));
                model3.setInstitution_city(characters.replace(Kurum_İli));
                model3.setStart_date(characters.replace(Başlangıç_Tarihi));
                model3.setFinal_date(characters.replace(Bitiş_Tarihi));
                model3.setSubject_of_contract(characters.replace(Sözleşme_Konusu));
                model3.setDoc(m);
                model3List.add(model3);

            }
            m.setWorkplaces(model3List);
            m.setEduList(modelList);
            doctor.save(m);
            System.out.println("3 st table is complete_______________________________________________________________________***");
            System.err.println("Going to back for next main row_______________________________________________________________________***");

//        driver.findElementByXPath("/html/body/center/div/div[2]/div[1]/div/a").click();
            driver.navigate().back();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void createXlsFileInfoBetFootball(List<BrowseDoctorArmaModel2> siteRowList, String
            sheetName, Workbook workbook) throws IOException {

//        CreationHelper createHelper = workbook.getCreationHelper();
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setColor(IndexedColors.RED.getIndex());
//
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
//
//        Row headerRow = sheet.createRow(0);
//
//        ArrayList<String> list = new ArrayList();
//
//        list.add("#");
//        list.add("Academic Title");
//        list.add("Title");
//        list.add("Name");
//        list.add("Surname");
//        list.add("Workplace");
//        list.add("Nationality");
//        list.add("Gender");
//        list.add("Birth Year");
//        list.add("Personnel Type");
//        list.add("Undergraduate School");
//        list.add("Proffessional Area of Interest");
//        list.add("Branch name");
//        list.add("School graduated from");
//        list.add("Diploma registration year");
//        list.add("Name of the institution");
//        list.add("Institution city");
//        list.add("Start date");
//        list.add("Final date");
//        list.add("Subject of contract");
//
//        // Create cells
//        for (int i = 0; i < list.size(); i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(list.get(i));
//            cell.setCellStyle(headerCellStyle);
//        }
//
//
//        // Create Cell Style for formatting Date
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
//
//        // Create Other rows and cells with employees data
//        int rowNum = 1;
//        int max = 6;
//        int x = 1;
//        for (BrowseDoctorArmaModel2 siteRow : siteRowList) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(x);
//            row.createCell(1).setCellValue(siteRow.getAkademik_Unvan());
//            row.createCell(2).setCellValue(siteRow.getUnvan());
//            row.createCell(3).setCellValue(siteRow.getAdı());
//            row.createCell(4).setCellValue(siteRow.getSoyadı());
//            row.createCell(5).setCellValue(siteRow.getWorkspace());
//            row.createCell(6).setCellValue(siteRow.getUyruk());
//            row.createCell(7).setCellValue(siteRow.getCinsiyet());
//            row.createCell(8).setCellValue(siteRow.getDoğum_Yılı());
//            row.createCell(9).setCellValue(siteRow.getPersonel_Tipi());
//            row.createCell(10).setCellValue(siteRow.getLisans_Okul());
//            row.createCell(11).setCellValue(siteRow.getMesleki_İlgi_Alanı());
//            row.createCell(12).setCellValue(siteRow.getBranş_Adı());
//            row.createCell(13).setCellValue(siteRow.getMezun_Olduğu_Okul());
//            row.createCell(14).setCellValue(siteRow.getDiploma_Tescil_Yılı());
//            row.createCell(15).setCellValue(siteRow.getKurum_Adı());
//            row.createCell(16).setCellValue(siteRow.getKurum_İli());
//            row.createCell(17).setCellValue(siteRow.getBaşlangıç_Tarihi());
//            row.createCell(18).setCellValue(siteRow.getBitiş_Tarihi());
//            row.createCell(19).setCellValue(siteRow.getSözleşme_Konusu());
//            x++;
//
//        }
//
//
//        // Resize all columns to fit the content size
//        for (int i = 0; i < list.size(); i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialise();
    }
}