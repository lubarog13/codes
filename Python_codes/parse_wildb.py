import requests
from tkinter import *
from bs4 import BeautifulSoup
root = Tk()
app = Frame(root)
headers = {
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36'
}

def get_html(url):
    r = requests.get(url)
    return r.text
def get_page_ads(html):
    soup = BeautifulSoup(html, 'html.parser')
    divs = soup.find('div', {'class': 'catalog_main_table j-products-container'})
    ads = divs.find_all('div', {'class': 'dtList-inner'})
    for ad in ads:
        cost = ad.find('ins', {'class': 'lower-price'}).text
        if len(cost)<=6:
            cost_i = int(cost[0:len(cost)-2]);
            if cost_i <= 300:
                brand = ad.find('strong', {'class': 'brand-name c-text-sm'}).text
                name = ad.find('span', {'class': 'goods-name c-text-sm'}).text
                href = 'https://www.wildberries.ru' + ad.find('a', {'class': 'ref_goods_n_p j-open-full-product-card'}).get('href')
                lbl = Label(app, text=cost[0:len(cost)-2]+" "+brand+" "+name+" "+href)
                lbl.grid()
                print(cost_i, brand, name, href)
url = 'https://www.wildberries.ru/catalog/0/search.aspx?search=%D0%BF%D1%80%D1%8F%D0%B6%D0%B0%20%D0%B4%D0%BB%D1%8F%20%D0%B2%D1%8F%D0%B7%D0%B0%D0%BD%D0%B8%D1%8F&xsearch=true'
session = requests.session()
response = session.get(url, headers=headers)
root.title("Wildberries")
app.grid()
get_page_ads(response.text)
root.mainloop()
