package me.hapyl.mmu3;

import me.hapyl.mmu3.command.*;
import me.hapyl.mmu3.command.brush.BrushCommand;
import me.hapyl.mmu3.test.CommandWithArguments;
import me.hapyl.spigotutils.module.command.CommandProcessor;
import me.hapyl.spigotutils.module.command.SimpleCommand;
import me.hapyl.spigotutils.module.command.SimplePlayerAdminCommand;
import me.hapyl.spigotutils.module.config.DataField;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

public class CommandRegistry {

    private final CommandProcessor processor;

    public CommandRegistry(Main plugin) {
        this.processor = new CommandProcessor(plugin);
    }

    public void registerCommands() {
        register(new StateChangerCommand("stateChanger"));
        register(new EditStandCommand("editStand"));
        register(new SpecialBlocksCommand("specialBlocks"));
        register(new CalculateCommand("calculate"));
        register(new BackCommand("back"));
        register(new CenterCommand("center"));
        register(new ConsoleCommand("console"));
        register(new SayCommand("say"));
        register(new ItemCreatorCommand("itemCreator"));
        register(new EntityRemovalCommand("entityRemoval"));
        register(new GameModeCommand("gm"));
        register(new OpenInventoryCommand("openInventory"));
        register(new RollCommand("roll"));
        register(new PersonalTimeCommandCommand("personalTime"));
        register(new PersonalWeatherCommand("personalWeather"));
        register(new ItemCommand("getItem"));
        register(new GameCommand("game"));
        register(new SightBlockCommand("sightBlock"));
        register(new RawCommand("raw"));
        register(new LockCommand("lock"));
        register(new HatCommand("hat"));
        register(new SpeedCommand("speed"));
        register(new CandleCommand("candle"));
        register(new ActionCommand("action"));
        register(new PacketCommand("playerpacket"));
        register(new CalculateRelative("relative"));
        register(new PingCommand("ping"));
        register(new BrushCommand("tinybrush"));

        // TEST
        register(new CommandWithArguments("_testcommandwitharguments"));

        registerTestCommand("datafields", (player, strings) -> {
            final PersistentPlayerData data = PersistentPlayerData.getData(player);

            try {
                for (Field field : data.getClass().getDeclaredFields()) {
                    final DataField annotation = field.getAnnotation(DataField.class);
                    if (annotation == null) {
                        continue;
                    }

                    final Object value = FieldUtils.readField(field, data, true);
                    Message.debug(
                            player,
                            "%s %s: %s",
                            "&7" + field.getName(),
                            "&e(" + field.getType().getSimpleName() + ")",
                            value == null ? "null" : value
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void registerTestCommand(String command, BiConsumer<Player, String[]> consumer) {
        register(new SimplePlayerAdminCommand("_test" + command) {
            @Override
            protected void execute(Player player, String[] strings) {
                consumer.accept(player, strings);
            }
        });
    }

    private void register(SimpleCommand command) {
        processor.registerCommand(command);
    }

}
