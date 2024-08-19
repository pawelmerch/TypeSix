import datetime

short_message = 'Ramp_test_{}'.format(datetime.datetime.now())
short_message = short_message.replace(".", "_")
short_message = short_message.replace(",", "_")
short_message = short_message.replace(":", "_")
short_message = short_message.replace(";", "_")
short_message = short_message.replace(" ", "_")
short_message = short_message.replace("-", "_")

print(short_message)