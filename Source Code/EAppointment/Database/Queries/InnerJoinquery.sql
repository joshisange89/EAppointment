
SELECT appointments.prospect_email, appointments.appointment_start_time, appointments.date,
prospect_students.first_name, prospect_students.last_name, prospect_email from appointments 
INNER JOIN prospect_students
ON appointments.staff_email='jaccy@yahoo.com' AND prospect_students.email = appointments.prospect_email;