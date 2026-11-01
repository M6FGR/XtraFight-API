package team.frostfield.xtrafight_api.network.interfaces;

@FunctionalInterface
public interface StreamMemberDecoder<O, T> {
    void decode(T value, O output);
}