ALTER TABLE reminders
ADD COLUMN reminder_day TINYINT default -1 AFTER reminder_interval;