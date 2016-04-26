SELECT DISTINCT ON day, timeslots, duration from setup_appointments where
		 email = "john@itu.com" ORDER BY FIELD(day, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')
			