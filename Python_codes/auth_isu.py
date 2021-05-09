from selenium import webdriver
import random
from auth_data import login, password

from selenium.webdriver.common.keys import Keys
import time

def login_():
    time.sleep(3)
    browser = webdriver.Chrome('chromedriver.exe')
    browser.get('https://isu.ifmo.ru/')
    username = browser.find_element_by_name("p_t14")
    username.clear()
    username.send_keys(login)
    time.sleep(3)
    user_password = browser.find_element_by_name("p_t15")
    user_password.clear()
    user_password.send_keys(password)
    user_password.send_keys(Keys.ENTER)
    time.sleep(10)
    browser.find_element_by_xpath('/html/body/form/div[3]/div[2]/div/div[1]/ul[1]/li[2]/a/span').click()
    time.sleep(10)
    browser.find_element_by_xpath('/html/body/form/div[2]/div[2]/div/div[1]/ul[1]/li[6]/a/span/span').click()
    time.sleep(3)
    browser.find_element_by_xpath('/html/body/form/div[2]/div[2]/div/div[1]/ul[1]/li[6]/ul/li[3]/a/span/span').click()
    time.sleep(1)
    browser.find_element_by_xpath('/html/body/div/form/div[5]/table/tbody/tr/td/div/div[3]/div/div/div[1]/div[1]/button/span').click()
    time.sleep(5)
    browser.find_element_by_xpath('/html/body/div[1]/div/table[1]/tbody/tr/td/div[1]/div[2]/input[2]').send_keys(login)
    time.sleep(5)
    browser.find_element_by_xpath('/html/body/div[1]/div/table[2]/tbody/tr[1]/td/input[2]').send_keys("Привет!")
    time.sleep(3)
    browser.find_element_by_xpath('/html/body/div[1]/a[3]').click()
login_()