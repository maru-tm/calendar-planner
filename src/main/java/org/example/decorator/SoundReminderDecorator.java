public class SoundReminderDecorator implements Reminder {
    private Reminder reminder;

    public SoundReminderDecorator(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public void remind() {
        reminder.remind();
        System.out.println("Звуковое напоминание.");
    }
}
