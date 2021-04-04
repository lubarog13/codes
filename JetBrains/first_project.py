# print("WE NEED\n\nTO LEARN PYTHON\n\nAS QUICKLY AS POSSIBLE\n\n")
"""form=""
while form !="!done":
    form = input("- Choose a formatter: ")
    if form == "!help":
        print("Available formatters: plain bold italic header link inline-code ordered-list unordered-list new-line\nSpecial commands: !help !done")
    elif form=="plain" or form=="bold" or form=="italic" or form=="header" or form=="link" or form=="inline-code" or form=="ordered-list" or form=="unordered-list" or form=="new-line" or form=="!done":
        pass
    else:
        print("Unknown formatting or command. Please try again")"""
""" pi = 3.141592653589793
print("%(num)1.5f"%{"num":pi}) """
""" user_city = "Istanbul"
def change_city(new_user_city):
    global user_city
    user_city = new_user_city
change_city("Paris")
print(user_city) """
""" def merge_lists(list_one, list_two):
    list  =list_one+list_two
    return list
list_one =['1', '1', '1']
list_two=['2', '2']
print(merge_lists(list_one=list_one, list_two=list_two)) """
number = int(input())
word = input()

# write a condition for plurals
if(number!=1):
    word+='s'
print(number, word)